package com.hayde117.write.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hayde117.util.model.Diary
import com.hayde117.write.WriteTopBar
import java.time.ZonedDateTime

@Preview(showBackground = true, name = "Default State")
@Composable
fun WriteTopBarPreview_Default() {
    WriteTopBar(
        selectedDiary = null,
        moodName = { "Happy" },
        onDateTimeUpdated = { /* No-op */ },
        onDeleteConfirmed = { /* No-op */ },
        onBackPressed = { /* No-op */ }
    )
}

@Preview(showBackground = true, name = "With Selected Diary")
@Composable
fun WriteTopBarPreview_SelectedDiary() {
    val sampleDiary = Diary()

    WriteTopBar(
        selectedDiary = sampleDiary,
        moodName = { "Happy" },
        onDateTimeUpdated = { /* No-op */ },
        onDeleteConfirmed = { /* No-op */ },
        onBackPressed = { /* No-op */ }
    )
}

@Preview(showBackground = true, name = "Date and Time Updated")
@Composable
fun WriteTopBarPreview_DateTimeUpdated() {
    WriteTopBar(
        selectedDiary = null,
        moodName = { "Reflective" },
        onDateTimeUpdated = { /* No-op */ },
        onDeleteConfirmed = { /* No-op */ },
        onBackPressed = { /* No-op */ }
    )
}
