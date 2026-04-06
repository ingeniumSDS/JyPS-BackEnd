// Source - https://stackoverflow.com/a/64658200
// Posted by Filip, modified by community. See post 'Timeline' for change history
// Retrieved 2026-04-05, License - CC BY-SA 4.0

package com.ingenium.jyps.config.mappstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.CLASS)
public @interface Default {
}
