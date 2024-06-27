package com.mustfaibra.roffu.screens.onboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mustfaibra.roffu.R
import com.mustfaibra.roffu.components.CustomButton
import com.mustfaibra.roffu.sealed.Orientation
import com.mustfaibra.roffu.ui.theme.Dimension
import com.mustfaibra.roffu.utils.addMoveAnimation

@Composable
fun OnboardScreen(
    onboardViewModel: OnboardViewModel = hiltViewModel(),
    onBoardFinished: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.walking_shoes)
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            speed = 2f,
            restartOnPlay = true
        )

        LottieAnimation(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            progress = { progress },
            composition = composition,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(Dimension.pagePadding),
            verticalArrangement = Arrangement.spacedBy(Dimension.sm),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .addMoveAnimation(
                        orientation = Orientation.Vertical,
                        from = (-100).dp,
                        to = 0.dp,
                        duration = 1000
                    ),
                text = buildAnnotatedString {
                    append("Yolculuklar her zaman ")
                    withStyle(
                        style = MaterialTheme.typography.h1
                            .copy(
                                fontFamily = FontFamily.Cursive,
                                color = MaterialTheme.colors.secondary,
                                fontWeight = FontWeight.Black
                            )
                            .toSpanStyle()
                    ) {
                        append(stringResource(id = R.string.app_name))
                    }
                    append(" ile başlar  🤩")
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                modifier = Modifier
                    .addMoveAnimation(
                        orientation = Orientation.Vertical,
                        from = 100.dp,
                        to = 0.dp,
                        duration = 1000
                    ),
                text = "\"Doğru Ayakkabı Nasıl Seçilir?\" videosunu izleyerek günlük aktiviteleriniz sırasında kendinizi daha rahat hissetmenizi sağlayacak havalı ayakkabılar bulun.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(Dimension.pagePadding))
            CustomButton(
                modifier = Modifier
                    .addMoveAnimation(
                        orientation = Orientation.Vertical,
                        from = 300.dp,
                        to = 0.dp,
                        duration = 1000
                    )
                    .padding(
                        horizontal = Dimension.pagePadding.times(2),
                        vertical = Dimension.pagePadding
                    ),
                text = stringResource(R.string.get_started),
                buttonColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.large,
                padding = PaddingValues(
                    vertical = Dimension.md,
                    horizontal = Dimension.pagePadding.times(2f)
                ),
                onButtonClicked = {
                    onboardViewModel.updateLaunchState(context = context)
                    onBoardFinished()
                },
                contentColor = MaterialTheme.colors.onPrimary
            )
            CustomButton(
                modifier = Modifier
                    .addMoveAnimation(
                        orientation = Orientation.Vertical,
                        from = 300.dp,
                        to = 0.dp,
                        duration = 1000
                    )
                    .padding(
                        horizontal = Dimension.pagePadding.times(2),
                        vertical = Dimension.pagePadding
                    ),
                text = "Videoyu İzle",
                buttonColor = MaterialTheme.colors.secondary,
                shape = MaterialTheme.shapes.large,
                padding = PaddingValues(
                    vertical = Dimension.md,
                    horizontal = Dimension.pagePadding.times(2f)
                ),
                onButtonClicked = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ys7fU9gR4kQ"))
                    context.startActivity(intent)
                },
                contentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}


