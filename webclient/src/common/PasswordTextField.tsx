import {ControlledTextField} from "./ControlledTextField";
import React from "react";

export function PasswordTextField() {
    return <ControlledTextField
        name={"password"}
        fullWidth
        label={"Password"}
        type="password"
        defaultValue={""}
        rules={{required: {value: true, message: "enter a password"}}}
    />;
}
