package com.vehiclecompanion.ui.places

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vehiclecompanion.R
import kotlinx.coroutines.launch

@Composable
fun PlacesTabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    val list = mutableListOf<String>()
    list.add("List")
    list.add("Map")

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 4.dp,
                color = colorResource(id = R.color.color_gray_dark)
            )
        },
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        modifier = Modifier.semantics {
                            contentDescription = list[index].lowercase()
                        },
                        text = list[index],
                        color = if (pagerState.currentPage == index)
                            colorResource(id = R.color.color_gray_dark)
                        else
                            colorResource(id = R.color.color_gray)
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}