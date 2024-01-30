package com.flag.flagcodepicker.ui.componants

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.flag.flagcodepicker.R

@Composable
fun paddingSmall(): Dp {
    return dimensionResource(id = R.dimen.space_small)
}

@Composable
fun paddingNormal(): Dp {
    return dimensionResource(id = R.dimen.space_normal)
}

@Composable
fun paddingHigher(): Dp {
    return dimensionResource(id = R.dimen.space_higher)
}


@Composable
fun paddingHuge(): Dp {
    return dimensionResource(id = R.dimen.space_huge)
}

