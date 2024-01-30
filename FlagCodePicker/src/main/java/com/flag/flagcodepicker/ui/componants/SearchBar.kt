package com.flag.flagcodepicker.ui.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.code.composecountrypicker.ui.theme.Gray100
import com.code.composecountrypicker.ui.theme.Gray200
import com.code.composecountrypicker.ui.theme.Gray500
import com.flag.flagcodepicker.R


@Composable
fun searchBar(
    onValueChange: (String) -> Unit,
    backgroundColor: Color = Gray100,
    iconSearchId: Int = R.drawable.ic_baseline_search_24,
    hintColor: Color = Gray200,
    borderColor: Color = Gray100,
    modifier: Modifier,
    searchedValue: String = "",
    isClearSearch: Boolean = false,
    onClearSearchClick: () -> Unit = {}
): String {

    var isShowHint by remember {
        mutableStateOf(true)
    }
    val textSearch = remember {
        mutableStateOf(searchedValue)
    }
    Row(
        modifier
            .padding(
                paddingNormal()
            )
            .border(
                width = dimensionResource(id = R.dimen.border_thin),
                color = borderColor, shape = RoundedCornerShape(paddingHuge())
            )
            .clip(shape = RoundedCornerShape(paddingHuge()))
            .background(backgroundColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = paddingNormal(), vertical = paddingNormal()),

        ) {
        Image(
            painter = painterResource(
                id = iconSearchId
            ),
            contentDescription = "search"
        )
        Spacer(modifier = Modifier.padding(paddingSmall()))
        Box {


            BasicTextField(
                modifier = modifier,
                value = textSearch.value,
                onValueChange = {
                    //  text = it
                    onValueChange(it)
                    textSearch.value = it
                    isShowHint = textSearch.value.isEmpty()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                maxLines = 1,
                cursorBrush = SolidColor(Color.Blue),
            )

            if (isShowHint && textSearch.value.isEmpty()) {
                val textVisible = stringResource(id = R.string.search)
                Text(
                    text = textVisible,
                    color = hintColor,
                    maxLines = 1,
                )
            }
            if (isClearSearch)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        modifier = Modifier
                            .size(19.dp)
                            .clickable {
                                isShowHint = true
                                textSearch.value = ""
                                onClearSearchClick()
                            },
                        painter = painterResource(
                            id = R.drawable.vector_close
                        ),
                        contentDescription = stringResource(id = R.string.clear_search),
                        colorFilter = ColorFilter.tint(Gray500)
                    )
                }
        }


    }
    return textSearch.value.trim()
}
