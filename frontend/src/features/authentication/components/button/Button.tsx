import type { ButtonHTMLAttributes } from "react";
import classes from "./Button.module.scss";

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  outline: boolean;
};

export function Button({ outline, ...options }: ButtonProps) {
  return (
    <button
      {...options}
      className={`${classes.button} ${outline ? classes.outline : ""}`}
    ></button>
  );
}
