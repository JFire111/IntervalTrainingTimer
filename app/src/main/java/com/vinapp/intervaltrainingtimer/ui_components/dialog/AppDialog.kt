package com.vinapp.intervaltrainingtimer.ui_components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun AppDialog(
    leftButton: DialogButton? = null,
    rightButton: DialogButton? = null,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(16.dp)
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            backgroundColor = AppTheme.colors.darkGray,
            shape = shape,
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    content()
                }
                if (leftButton != null || rightButton != null) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = AppTheme.colors.mediumGray
                    )
                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        leftButton?.let {
                            DialogButton(it)
                        }
                        if (leftButton != null && rightButton != null) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                color = AppTheme.colors.mediumGray
                            )
                        }
                        rightButton?.let {
                            DialogButton(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.DialogButton(button: DialogButton) {
    Box(
        modifier = Modifier
            .weight(1F)
            .clickable(
                onClick = button.onClick
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            text = button.text,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.title.copy(
                color = button.textColor ?: AppTheme.colors.whiteFontColor
            )
        )
    }
}

data class DialogButton(
    val text: String,
    val textColor: Color? = null,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AppDialog(
                leftButton = DialogButton(
                    text = "Cancel",
                    onClick = {}
                ),
                rightButton = DialogButton(
                    text = "Ok",
                    onClick = {}
                ),
                onDismissRequest = {}
            ) {}
        }
    }
}