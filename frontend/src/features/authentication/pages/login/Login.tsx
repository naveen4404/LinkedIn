import { Link } from "react-router-dom";
import { Box } from "../../components/box/Box";
import { Button } from "../../components/button/Button";
import { Input } from "../../components/input/Input";
import { Layout } from "../../components/layout/Layout";
import classes from "./Login.module.scss";
import { Seperator } from "../../components/seperator/Seperator";

export function Login() {
  return (
    <Layout className={classes.root}>
      <Box>
        <h1>Sign in</h1>
        <p>Stay updated on your professional world.</p>
        <Input label="Email" type="email" id="email"></Input>
        <Input label="Password" type="password" id="password"></Input>
        <Button type="submit" outline={false}>
          sign in
        </Button>
        <Link to="/request-password-reset">Forgot password?</Link>
      </Box>
      <Seperator>Or</Seperator>
      <div className={classes.register}>
        New to LinkedIn? {<Link to="/signup">Join now</Link>}
      </div>
    </Layout>
  );
}
