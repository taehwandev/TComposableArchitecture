package tech.thdev.composable.architecture.sample.feature.main.screen.settings.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.R

@Composable
internal fun DarkOnOff(
    selected: Boolean,
    title: String,
    res: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.DarkGray).takeIf { selected },
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(res),
                contentDescription = null,
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )

            RadioButton(
                selected = selected,
                onClick = onClick,
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun PreviewDarkOnOff() {
    Row {
        DarkOnOff(
            selected = true,
            title = "Auto",
            onClick = {},
            res = R.drawable.img_dark_auto,
            modifier = Modifier
                .weight(1f)
        )
        DarkOnOff(
            selected = false,
            title = "Dark",
            onClick = {},
            res = R.drawable.img_dark_on,
            modifier = Modifier
                .weight(1f)
        )
        DarkOnOff(
            selected = false,
            title = "Light",
            onClick = {},
            res = R.drawable.img_dark_off,
            modifier = Modifier
                .weight(1f)
        )
    }
}
