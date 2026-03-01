import { useState, type SubmitEvent } from "react";
import { Box } from "../../components/box/Box";
import { Button } from "../../components/button/Button";
import { Input } from "../../components/input/Input";
import { Layout } from "../../components/layout/Layout";
import classes from "./EmailVerification.module.scss";
import { useNavigate } from "react-router-dom";
export function EmailVerification() {
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const sendEmailVerificationToken = async () => {
    setMessage("");
    try {
      setIsLoading(true);
      const response = await fetch(
        import.meta.env.VITE_API_URL +
          "/api/v1/authentication/send-email-verification-token",
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        },
      );
      if (response.ok) {
        setErrorMessage("");
        setMessage("Code sent successfully. Please check your email.");
        return;
      }
      const { message } = await response.json();
      setErrorMessage(message);
    } catch (e) {
      console.log(e);
      setErrorMessage("Something went wrong, please try again");
    } finally {
      setIsLoading(false);
    }
  };

  const validateEmail = async (code: string) => {
    setMessage("");
    try {
      setIsLoading(true);
      const response = await fetch(
        import.meta.env.VITE_API_URL +
          `/api/v1/authentication/validate-email-verification-token?token=${code}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        },
      );
      if (response.ok) {
        setErrorMessage("");
        navigate("/");
        return;
      }
      const { message } = await response.json();
      setErrorMessage(message);
    } catch (e) {
      console.log(e);
      setErrorMessage("Something went wrong, please tyy again.");
    } finally {
      setIsLoading(false);
    }
  };

  const handleValidateEmail = async (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const code = e.currentTarget.code.value;
    await validateEmail(code);
  };

  return (
    <Layout className={classes.root}>
      <Box>
        <h1>Verify your email</h1>
        <p>
          Only one step left to complete your registration. Verify your email
          address.
        </p>
        <form onSubmit={handleValidateEmail}>
          <Input
            label="Verification code"
            type="text"
            name="code"
            id="code"
          ></Input>
          {message && <p className={classes.success}>{message}</p>}
          {errorMessage && <p className={classes.error}>{errorMessage}</p>}
          <Button type="submit" disabled={isLoading}>
            {isLoading ? "..." : "Validate email"}
          </Button>
          <Button
            type="button"
            outline
            onClick={() => {
              sendEmailVerificationToken();
            }}
          >
            {isLoading ? "..." : "Send again"}
          </Button>
        </form>
      </Box>
    </Layout>
  );
}
