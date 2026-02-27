import { useState } from "react";
import { Box } from "../../components/box/Box";
import { Button } from "../../components/button/Button";
import { Input } from "../../components/input/Input";
import { Layout } from "../../components/layout/Layout";
import classes from "./Signup.module.scss";
import { Seperator } from "../../components/seperator/Seperator";
import { Link } from "react-router-dom";

export function Signup() {
  const [errorMessage, setErrorMessage] = useState("");
  return (
    <Layout className={classes.root}>
      <Box>
        <h1>Sign up</h1>
        <p>Make the most of your professional life.</p>
        <form>
          <Input label="Email" type="email" id="email"></Input>
          <Input label="Password" type="password" id="password"></Input>
          {errorMessage && <p className={classes.error}>error</p>}
          <p className={classes.disclaimer}>
            By clicking Agree & Join or Continue, you agree to LinkedIn's{" "}
            <a href="">User Agreement</a>, <a href="">Privacy Policy</a>, and{" "}
            <a href="">Cookie Policy</a>.
          </p>
          <Button outline={false} type="submit">
            Agree & join
          </Button>
        </form>
        <Seperator>Or</Seperator>
        <div className={classes.register}>
          Already on LinkedIn? <Link to="/login">Sign in</Link>
        </div>
      </Box>
    </Layout>
  );
}
