package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.ColorUtils
import com.plcoding.cleanarchitecturenoteapp.core.util.TestTags
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
            .testTag(TestTags.NOTE_ITEM)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    CleanArchitectureNoteAppTheme {
        NoteItem(
            note = Note(
                title = "Sample Note Title",
                content = "This is a sample note content to demonstrate how the note item looks in the app. It can contain multiple lines of text.",
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[0].hashCode(),
                id = 1
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onDeleteClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Long Content")
@Composable
fun NoteItemLongContentPreview() {
    CleanArchitectureNoteAppTheme {
        NoteItem(
            note = Note(
                title = "Very Long Title That Might Get Truncated",
                content = "This is a much longer note content to test how the UI handles overflow. " +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.",
                timestamp = System.currentTimeMillis(),
                color = Note.noteColors[1].hashCode(),
                id = 2
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onDeleteClick = { }
        )
    }
}

@Preview(showBackground = true, name = "All Colors")
@Composable
fun NoteItemAllColorsPreview() {
    CleanArchitectureNoteAppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Note.noteColors.forEachIndexed { index, color ->
                NoteItem(
                    note = Note(
                        title = "Note ${index + 1}",
                        content = "This note uses color variant ${index + 1}",
                        timestamp = System.currentTimeMillis(),
                        color = color.hashCode(),
                        id = index
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    onDeleteClick = { }
                )
            }
        }
    }
}