import type { InputHTMLAttributes } from "react";
import classes from "./Input.module.scss";

type InputProps = InputHTMLAttributes<HTMLInputElement> & {
  label: string;
};

export function Input({ label, ...options }: InputProps) {
  return (
    <div className={classes.root}>
      <label>{label}</label>
      <input {...options} />
    </div>
  );
}
