// SelectionGuideScreen.kt
package com.mustfaibra.roffu.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustfaibra.roffu.R

@Composable
fun SelectionGuideScreen() {
    val backgroundColor = Color(0xFFE0F7FA)
    val primaryColor = Color(0xFF00695C) // Primary color to match the splash screen
    val accentColor = Color(0xFF004D40) // Accent color
    val buttonColor = Color(0xFF004D40) // Button color to match splash screen theme
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(primaryColor.copy(alpha = 0.5f), primaryColor)
    )

    var imageSize by remember { mutableStateOf(1f) }
    val imageScaleAnim = animateFloatAsState(
        targetValue = imageSize,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header Section
        Text(
            text = "Doğru Ayakkabı Nasıl Seçilir?",
            style = MaterialTheme.typography.h4.copy(
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Introductory Text
        Text(
            text = "Doğru Ayakkabı Seçimi:",
            style = MaterialTheme.typography.h5.copy(
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Images Gallery Section with animation
        Text(
            text = "Ayakkabı Modelleri:",
            style = MaterialTheme.typography.h6.copy(
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(R.drawable.logo, R.drawable.logo, R.drawable.logo).forEach { image ->
                ImageBox(imageRes = image, imageScaleAnim = imageScaleAnim.value)
            }
        }

        // Shoe Selection Tips
        Text(
            text = "Doğru Ayakkabı Seçimi Nasıl Yapılır?",
            style = MaterialTheme.typography.h5.copy(
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        ShoeSelectionTip(title = "Boyut:", tips = listOf(
            "Ayak ölçünüzü alın ve deneyerek alın.",
            "Her iki ayağınızı da ölçün; büyük olana göre seçim yapın."
        ))

        Spacer(modifier = Modifier.height(4.dp))
        ShoeSelectionTip(title = "Model:", tips = listOf(
            "Ayağınızın şekline uygun model seçin."
        ))

        Spacer(modifier = Modifier.height(4.dp))
        ShoeSelectionTip(title = "Deneyim:", tips = listOf(
            "Yeni ayakkabıyla mağazada yürüyün; rahatsa alın.",
            "Alışverişi akşam yapın; ayaklar genişler."
        ))

        Spacer(modifier = Modifier.height(4.dp))
        ShoeSelectionTip(title = "Tavsiyeler:", tips = listOf(
            "Sıkı ayakkabı giymeyin.",
            "Hava alan malzemeler tercih edin.",
            "Kullanım amacına uygun ayakkabı seçin.",
            "Ayakkabıları düzenli olarak yenileyin.",
            "Not: Doğru ayakkabı, ayak sağlığı ve konfor için önemlidir."
        ))

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Menu at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush, shape = RoundedCornerShape(12.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationButton(text = "About Us", onClick = { /*TODO*/ })
            NavigationButton(text = "Continue", onClick = { /*TODO*/ })
        }

        LaunchedEffect(Unit) {
            imageSize = 1.2f
        }
    }
}

@Composable
fun NavigationButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF004D40)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.button)
    }
}

@Composable
fun ImageBox(imageRes: Int, imageScaleAnim: Float) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .graphicsLayer {
                scaleX = imageScaleAnim
                scaleY = imageScaleAnim
            }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ShoeSelectionTip(title: String, tips: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(Color(0xFFE0F7FA), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(
                color = Color(0xFF00695C),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        )
        tips.forEach { tip ->
            Text(
                text = "• $tip",
                style = MaterialTheme.typography.body2.copy(color = Color.DarkGray),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionGuideScreenPreview() {
    SelectionGuideScreen()
}
