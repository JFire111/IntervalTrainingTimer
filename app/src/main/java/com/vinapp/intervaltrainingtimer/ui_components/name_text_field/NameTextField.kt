package com.vinapp.intervaltrainingtimer.ui_components.name_text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    placeholderText: String? = null,
    onValueChange: (value: String) -> Unit
) {
    val inputTextFieldShape = RoundedCornerShape(16.dp)
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        textStyle = AppTheme.typography.regular.copy(
            color = AppTheme.colors.whiteFontColor
        ),
        placeholder = {
            placeholderText?.let {
                Text(
                    text = it,
                    style = AppTheme.typography.regular.copy(
                        color = AppTheme.colors.grayFontColor
                    )
                )
            }
        },
        shape = inputTextFieldShape,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = AppTheme.colors.red,
            focusedBorderColor = AppTheme.colors.lightGray,
            unfocusedBorderColor = AppTheme.colors.mediumGray,
            errorBorderColor = AppTheme.colors.red,
        )
    )
}