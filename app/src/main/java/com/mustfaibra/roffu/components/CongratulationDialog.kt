package com.mustfaibra.roffu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CongratulationDialog(
    onDismiss: () -> Unit,
    onContinueShopping: () -> Unit,
    onGoToSurvey: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Congratulations!",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "Your order has been placed successfully.",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = onContinueShopping, modifier = Modifier.weight(1f)) {
                        Text(text = "Continue Shopping")
                    }
                    Button(onClick = onGoToSurvey, modifier = Modifier.weight(1f)) {
                        Text(text = "Go to Survey")
                    }
                }
            }
        }
    }
}
