import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDeleteTextTap: () -> Unit,
    placeholder: String? = null,
) {

    val colorScheme = MaterialTheme.colorScheme
    val surfaceColor = colorScheme.surface
    val onSurfaceColor = colorScheme.onSurface
    val transparentColor = Color.Transparent

    TextField(modifier = modifier
        .fillMaxWidth()
        .shadow(
            elevation = 5.dp,
            shape = RoundedCornerShape(50),
        ),
        maxLines = 1,
        shape = RoundedCornerShape(50),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = transparentColor,
            unfocusedIndicatorColor = transparentColor,
            unfocusedContainerColor = surfaceColor,
            focusedContainerColor = surfaceColor

        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon",
                tint = onSurfaceColor
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = onDeleteTextTap) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Delete Text Icon",
                        tint = onSurfaceColor
                    )
                }
            }
        },
        placeholder = { if (placeholder != null) Text(text = placeholder) })
}

@Preview(showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField(
        modifier = Modifier.width(200.dp),
        value = "Victor Test", onValueChange = {},
        onDeleteTextTap = {}
    )
}