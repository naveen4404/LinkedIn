import type { ReactNode } from "react";
import classes from "./Box.module.scss";

interface BoxProp {
  children: ReactNode;
}

export function Box({ children }: BoxProp) {
  return <div className={classes.root}>{children}</div>;
}
