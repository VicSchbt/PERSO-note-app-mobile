package com.example.noteapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.config.ModalConfig
import com.example.noteapp.ui.theme.Neutral100
import com.example.noteapp.ui.theme.Neutral200
import com.example.noteapp.ui.theme.Neutral600
import com.example.noteapp.ui.theme.Neutral700
import com.example.noteapp.ui.theme.Neutral950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationModal(
    config: ModalConfig,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onValidateClick: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest
    ) {
        Surface(
            shape = MaterialTheme.shapes.small
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Neutral100, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = config.leadingIconRes),
                            contentDescription = "",
                            tint = Neutral950,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            stringResource(config.title),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            lineHeight = 18.sp

                        )
                        Text(
                            stringResource(config.text),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 16.sp,
                            color = Neutral700
                        )
                    }
                }
                HorizontalDivider(
                    color = Neutral200
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 16.dp
                        ),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onCancelClick,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Neutral100,
                            contentColor = Neutral600
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                    Button(
                        onClick = onValidateClick,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = config.accentColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(config.validateCTA))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmationModalPreviewDelete() {
    ConfirmationModal(ModalConfig.DELETE, {}, {}, {})
}

@Preview(showBackground = true)
@Composable
fun ConfirmationModalPreviewArchive() {
    ConfirmationModal(ModalConfig.ARCHIVE, {}, {}, {})
}