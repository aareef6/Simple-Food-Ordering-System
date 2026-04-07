package com.example.androidmainassessment3.commoncomposable.dialoges

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidmainassessment3.ui.theme.CardBack
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel

@Composable
fun ProductDeletingAlertDialog(deleteDialogState: MutableState<Boolean>,
                            vModel: ProductViewModel,taskId: MutableState<Int>,deleteButton:()-> Unit) {
    val buttonColor = Color(0xFF9B6FDB)

    AlertDialog(
        onDismissRequest = { deleteDialogState.value = false },
        confirmButton = {
            Button(
//                onClick = {
//                    vModel.deleteSingleTaskData(taskId.value)
//                    deleteDialogState.value = false
//                },
                onClick = {
                    deleteButton()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFE6E6)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(140.dp)
            ) {
                Text(
                    "Delete",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold
                )
            }

        },
        dismissButton = {
            OutlinedButton(
                onClick = { deleteDialogState.value = false },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text(
                text = "Delete Item",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete this item? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}