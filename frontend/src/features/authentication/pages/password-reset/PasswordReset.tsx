import { useState, type SubmitEvent } from "react";
import { useNavigate } from "react-router-dom";
import { Box } from "../../components/box/Box";
import { Button } from "../../components/button/Button";
import { Input } from "../../components/input/Input";
import { Layout } from "../../components/layout/Layout";

export function PasswordReset() {
  const navigate = useNavigate();
  const [emailSent, setEmailSent] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [email, setEmail] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const sendPasswordResetToken = async (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    const email = e.currentTarget.email.value;
    try {
      setIsLoading(true);
      const response = await fetch(
        import.meta.env.VITE_API_URL +
          `/api/v1/authentication/send-password-reset-token?email=${email}`,
        {
          method: "PUT",
        },
      );
      if (response.ok) {
        setErrorMessage("");
        setEmailSent(true);
        setEmail(email);
        return;
      }
      const { message } = await response.json();
      setErrorMessage(message);
    } catch (e) {
      console.log(e);
      setErrorMessage("Something went wrong.");
    } finally {
      setIsLoading(false);
    }
  };

  const resetPassword = async (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const code = e.currentTarget.code.value;
    const newPassword = e.currentTarget.newpassword.value;

    try {
      const response = await fetch(
        import.meta.env.VITE_API_URL +
          `/api/v1/authentication/reset-password?newPassword=${newPassword}&token=${code}&email=${email}`,
        {
          method: "PUT",
        },
      );
      if (response.ok) {
        setErrorMessage("");
        navigate("/login");
        return;
      }

      const { message } = await response.json();
      setErrorMessage(message);
    } catch (e) {
      console.log(e);
      setErrorMessage("Something went wrong, please try again.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Layout>
      <Box>
        <h1>Reset password</h1>

        {!emailSent ? (
          <>
            <p>
              Enter your email and we'll send a verification code if it matches
              an existing LinkedIn account.
            </p>
            <form onSubmit={sendPasswordResetToken}>
              <Input label="Email" type="email" id="email" name="email"></Input>
              <p style={{ color: "red" }}>{errorMessage}</p>
              <Button type="submit" disabled={isLoading}>
                {isLoading ? "..." : "Next"}
              </Button>

              <Button
                type="button"
                outline
                onClick={() => navigate("/login")}
                disabled={isLoading}
              >
                {isLoading ? "..." : "Back"}{" "}
              </Button>
            </form>
          </>
        ) : (
          <>
            <p>
              Enter the verification code sent to your email and your new
              password.
            </p>
            <form onSubmit={resetPassword}>
              <Input
                label="Reset code"
                type="text"
                id="code"
                name="code"
              ></Input>
              <Input
                label="New password"
                type="password"
                id="newpassword"
                name="newpassword"
              ></Input>
              <p style={{ color: "red" }}>{errorMessage}</p>
              <Button type="submit" disabled={isLoading}>
                {isLoading ? "..." : "Next"}
              </Button>
              <Button
                type="button"
                outline
                onClick={() => setEmailSent(false)}
                disabled={isLoading}
              >
                {isLoading ? "..." : "Back"}
              </Button>
            </form>
          </>
        )}
      </Box>
    </Layout>
  );
}
