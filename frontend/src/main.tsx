import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.scss";
import { EmailVerification } from "./features/authentication/pages/email-verification/EmailVerification";
import { Login } from "./features/authentication/pages/login/Login";
import { Signup } from "./features/authentication/pages/signup/Signup";
import { PasswordReset } from "./features/authentication/pages/password-reset/PasswordReset";

const router = createBrowserRouter([
  {
    path: "/",
    element: "Home",
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/signup",
    element: <Signup />,
  },
  {
    path: "/request-password-reset",
    element: <PasswordReset />,
  },
  {
    path: "/verify-email",
    element: <EmailVerification />,
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
);
