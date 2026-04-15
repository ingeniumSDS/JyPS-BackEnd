#!/bin/sh
set -eu

SOCKET_PATH="/tmp/tailscaled.sock"
STATE_DIR="/var/lib/tailscale"

mkdir -p "$STATE_DIR"

# Start tailscaled in userspace mode to maximize Docker Desktop compatibility.
tailscaled --state="${STATE_DIR}/tailscaled.state" --socket="$SOCKET_PATH" --tun=userspace-networking &
TS_PID=$!

until tailscale --socket="$SOCKET_PATH" status >/dev/null 2>&1; do
  sleep 1
done

: "${TS_AUTHKEY:?TS_AUTHKEY is required in .env}"

tailscale --socket="$SOCKET_PATH" up \
  --authkey="$TS_AUTHKEY" \
  --hostname="${TS_HOSTNAME:-jyps-front}" \
  --accept-dns=false \
  --accept-routes

# Ensure stale serve/funnel mappings do not survive restarts.
tailscale --socket="$SOCKET_PATH" funnel reset >/dev/null 2>&1 || true
tailscale --socket="$SOCKET_PATH" serve reset >/dev/null 2>&1 || true

TARGET="${TS_FUNNEL_TARGET:-http://jyps-front:80}"
TAILNET_HTTP_PORT="${TS_TAILNET_HTTP_PORT:-10000}"
FUNNEL_HTTPS_PORT="${TS_FUNNEL_HTTPS_PORT:-443}"

# Tailnet-only endpoint for diagnostics and private access.
tailscale --socket="$SOCKET_PATH" serve --bg --http="$TAILNET_HTTP_PORT" "$TARGET"

if [ "${TS_ENABLE_FUNNEL:-false}" = "true" ]; then
  # Public endpoint via Funnel, directly targeting the frontend service.
  tailscale --socket="$SOCKET_PATH" funnel --bg --https="$FUNNEL_HTTPS_PORT" "$TARGET"
fi

wait "$TS_PID"
