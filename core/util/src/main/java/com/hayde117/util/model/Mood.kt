package com.hayde117.util.model

import androidx.compose.ui.graphics.Color
import com.hayde117.ui.theme.AngryColor
import com.hayde117.ui.theme.AwfulColor
import com.hayde117.ui.theme.BoredColor
import com.hayde117.ui.theme.CalmColor
import com.hayde117.ui.theme.DepressedColor
import com.hayde117.ui.theme.DisappointedColor
import com.hayde117.ui.theme.HappyColor
import com.hayde117.ui.theme.HumorousColor
import com.hayde117.ui.theme.LonelyColor
import com.hayde117.ui.theme.MysteriousColor
import com.hayde117.ui.theme.NeutralColor
import com.hayde117.ui.theme.RomanticColor
import com.hayde117.ui.theme.ShamefulColor
import com.hayde117.ui.theme.SurprisedColor
import com.hayde117.ui.theme.SuspiciousColor
import com.hayde117.ui.theme.TenseColor
import com.hayde117.ui.R

enum class Mood(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color
) {
    Neutral(
        icon = R.drawable.neutral,
        contentColor = Color.Black,
        containerColor = NeutralColor
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = Color.Black,
        containerColor = HappyColor
    ),
    Angry(
        icon = R.drawable.angry,
        contentColor = Color.White,
        containerColor = AngryColor
    ),
    Bored(
        icon = R.drawable.bored,
        contentColor = Color.Black,
        containerColor = BoredColor
    ),
    Calm(
        icon = R.drawable.calm,
        contentColor = Color.Black,
        containerColor = CalmColor
    ),
    Depressed(
        icon = R.drawable.depressed,
        contentColor = Color.Black,
        containerColor = DepressedColor
    ),
    Disappointed(
        icon = R.drawable.disappointed,
        contentColor = Color.White,
        containerColor = DisappointedColor
    ),
    Humorous(
        icon = R.drawable.humorous,
        contentColor = Color.Black,
        containerColor = HumorousColor
    ),
    Lonely(
        icon = R.drawable.lonely,
        contentColor = Color.White,
        containerColor = LonelyColor
    ),
    Mysterious(
        icon = R.drawable.mysterious,
        contentColor = Color.Black,
        containerColor = MysteriousColor
    ),
    Romantic(
        icon = R.drawable.romantic,
        contentColor = Color.White,
        containerColor = RomanticColor
    ),
    Shameful(
        icon = R.drawable.shameful,
        contentColor = Color.White,
        containerColor = ShamefulColor
    ),
    Awful(
        icon = R.drawable.awful,
        contentColor = Color.Black,
        containerColor = AwfulColor
    ),
    Surprised(
        icon = R.drawable.surprised,
        contentColor = Color.Black,
        containerColor = SurprisedColor
    ),
    Suspicious(
        icon = R.drawable.suspicious,
        contentColor = Color.Black,
        containerColor = SuspiciousColor
    ),
    Tense(
        icon = R.drawable.tense,
        contentColor = Color.Black,
        containerColor = TenseColor
    )
}