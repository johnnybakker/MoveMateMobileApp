package nl.johnny.movemate.helpers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage

object ColorHelper {

    fun readColor(node: SemanticsNodeInteraction): Color {
        val color = IntArray(1)
        val image = node.captureToImage()

        image.readPixels(color,
            image.width / 4, image.height / 2, 1, 1)

        return Color(color[0])
    }

}