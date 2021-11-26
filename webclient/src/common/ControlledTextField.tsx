import {TextField, TextFieldProps} from "@material-ui/core";
import React from "react";
import {Controller, useFormContext} from "react-hook-form";
import {RegisterOptions} from "react-hook-form/dist/types/validator";

export type ControlledTextFieldProps = TextFieldProps & {
    name: string // Make name is a required field, because this is the form fields name.
    rules?: Omit<RegisterOptions, 'valueAsNumber' | 'valueAsDate' | 'setValueAs' | 'disabled'>;
}

export const ControlledTextField = ({
                                        name,
                                        defaultValue,
                                        rules,
                                        helperText,
                                        ...other
                                    }: ControlledTextFieldProps) => {

    return (
        <Controller
            defaultValue={defaultValue}
            rules={rules}
            name={name}
            {...useFormContext()}
            render={({field, fieldState}) => {
                let hasError = !!fieldState.error;
                return (
                    <TextField
                        helperText={hasError ? fieldState.error?.message : helperText}
                        error={hasError}
                        {...Object.assign({}, field, other)}
                    />
                )
            }}
        />
    );
};
