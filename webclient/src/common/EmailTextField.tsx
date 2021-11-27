import {ControlledTextField} from "./ControlledTextField";
import React from "react";

export function EmailTextField() {
    return <ControlledTextField
        name={"username"}
        fullWidth
        label={"E-Mail"}
        autoComplete={"email"}
        defaultValue={""}
        rules={{
            pattern: {
                value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                message: "enter a valid e-mail address"
            },
            required: {value: true, message: "enter an e-mail address"}
        }}
    />;
}
