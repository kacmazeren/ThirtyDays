package com.eren.thirtydays

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eren.thirtydays.model.Advice
import com.eren.thirtydays.ui.theme.ThirtyDaysTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AdviceList(advices: List<Advice>,
               modifier: Modifier = Modifier,
               contentPadding: PaddingValues = PaddingValues(0.dp),){
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    // Fade in entry animation for the entire list
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(advices) { index, advice ->
                AdviceListItem(
                    advice = advice,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        // Animate each list item to slide in vertically
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessVeryLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) } // staggered entrance
                            )
                        )
                )
            }
        }
    }
}
@Composable
fun AdviceListItem(
    advice: Advice,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth().height(300.dp)
            .border(5.dp, color = Color.Blue),
        ) {
        Column(
            modifier = Modifier
                .weight(1f).fillMaxWidth()
                .padding(16.dp).border(5.dp, color = Color.Red)
        ) {
            Column(modifier = Modifier
                .padding(5.dp).fillMaxWidth().height(200.dp)
                .border(5.dp, color = Color.Yellow),
            verticalArrangement = Arrangement.Top) {
                Image(painter = painterResource(advice.imageRes),
                    modifier = Modifier.fillMaxWidth().padding(16.dp).weight(4f,true),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillBounds
                    )
            }
            Spacer(Modifier.width(16.dp))
        Row() {
            Text(text = stringResource(id = advice.adviceString),
                modifier = Modifier.padding(16.dp))
        }
        }
    }
}
@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AdvicePreview() {
    val advice = Advice(
        R.string.advice_string1,
        R.drawable.android_superhero1
    )
    ThirtyDaysTheme {
        AdviceListItem(advice = advice)
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPrseview() {
    ThirtyDaysTheme {
        ThirtyDaysApp()
    }
}