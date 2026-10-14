const fs=require('fs');
const p='app/src/main/java/com/example/finalappmoreforensicsandfiction/ui/screens/BodyExaminationScreen.kt';
let s=fs.readFileSync(p,'utf8');
const a=s.indexOf('                    Box(modifier = Modifier.fillMaxSize())');
const b=s.indexOf('            // Accessible Text-List',a);
s=s.slice(0,a)+`                    BodyDiagram(imageBitmap, selectedViewNameState, if (showMarkersState) markers else emptyList(), scale, offsetX, offsetY) { selectedMarker = it }
                }
            }

`+s.slice(b);
s+=`
@Composable
private fun BodyDiagram(image: androidx.compose.ui.graphics.ImageBitmap?, view: String, markers: List<ExaminationMarker>, scale: Float, panX: Float, panY: Float, onSelect: (ExaminationMarker) -> Unit) {
    androidx.compose.foundation.layout.BoxWithConstraints(Modifier.fillMaxSize().background(Color.White)) {
        if (image == null) { Text("Examination image unavailable"); return@BoxWithConstraints }
        val range = when(view) {
            "Left" -> 0.01f to 0.24f
            "Front" -> 0.23f to 0.51f
            "Back" -> 0.505f to 0.755f
            "Right" -> 0.745f to 0.99f
            else -> 0f to 1f
        }
        val density = androidx.compose.ui.platform.LocalDensity.current
        val widthPx = with(density) { maxWidth.toPx() }
        val heightPx = with(density) { maxHeight.toPx() }
        val cropX = (range.first * image.width).toInt()
        val cropWidth = ((range.second-range.first) * image.width).toInt()
        val factor = minOf(widthPx/cropWidth, heightPx/image.height)
        val drawnWidth = cropWidth * factor
        val drawnHeight = image.height * factor
        val left = (widthPx-drawnWidth)/2
        val top = (heightPx-drawnHeight)/2
        Box(Modifier.fillMaxSize().graphicsLayer(scaleX=scale, scaleY=scale, translationX=panX, translationY=panY)) {
            Canvas(Modifier.fillMaxSize()) {
                drawImage(image, srcOffset=androidx.compose.ui.unit.IntOffset(cropX,0),
                    srcSize=androidx.compose.ui.unit.IntSize(cropWidth,image.height),
                    dstOffset=androidx.compose.ui.unit.IntOffset(left.toInt(),top.toInt()),
                    dstSize=androidx.compose.ui.unit.IntSize(drawnWidth.toInt(),drawnHeight.toInt()))
            }
            markers.filter { view == "Overview" || it.viewName == view }.forEachIndexed { index, marker ->
                val x = left + (marker.xRatio * image.width - cropX) * factor
                val y = top + marker.yRatio * image.height * factor
                Box(Modifier.offset { androidx.compose.ui.unit.IntOffset((x-with(density){24.dp.toPx()}).toInt(),(y-with(density){24.dp.toPx()}).toInt()) }
                    .size(48.dp).clip(CircleShape).background(DeepOxblood)
                    .clickable { onSelect(marker) }, contentAlignment=Alignment.Center) {
                    Text((index+1).toString(), color=WarmParchment)
                }
            }
        }
    }
}
`;
// offset extension is not part of existing explicit imports
s=s.replace('import androidx.compose.foundation.Canvas','import androidx.compose.foundation.layout.offset\nimport androidx.compose.foundation.Canvas');
fs.writeFileSync(p,s);
