package com.ummah.mosque.common.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val EMPTY_STRING = ""


@Composable
fun CustomTextField(
    enabled: Boolean,
    label: String,
    labelStyle: TextStyle,
    placeholderText: String,
    placeholderStyle: TextStyle,
    placeholderColor: Color = Color.Black,
    fieldHeight: Int = 48,
    modifier: Modifier,
    defaultValue: String = EMPTY_STRING,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    roundedCornersSize: Dp = 12.dp,
    maxLines: Int = 1,
    onValueChanged: (String) -> Unit,
    containerColor: Color = TextField_Background,
    textColor: Color = Color.Black,
    labelColor: Color,
    onKeyboardAction: (Action) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var fieldValue by remember {
        mutableStateOf(defaultValue)
    }
    Column(modifier = modifier) {
        if (enabled) {
            EnabledText(
                text = label,
                style = labelStyle,
                modifier = Modifier
                    .padding(bottom = 8.dp),
                labelColor = labelColor
            )
            EnabledTextField(
                value = fieldValue,
                placeholder = placeholderText,
                placeholderStyle = placeholderStyle,
                imeAction = imeAction,
                keyboardType = keyboardType,
                roundedCorners = roundedCornersSize,
                maxLines = maxLines,
                fieldHeight = fieldHeight,
                onValueChanged = {
                    fieldValue = it
                    onValueChanged(it)
                },
                placeholderColor = placeholderColor,
                containerColor = containerColor,
                textColor = textColor,
                onKeyboardAction = onKeyboardAction,
                visualTransformation = visualTransformation
            )
        } else {
            DisabledText(
                text = label,
                style = labelStyle,
                modifier = Modifier
                    .padding(bottom = 8.dp),
                color = placeholderColor
            )
            DisabledTextField(
                value = fieldValue,
                placeholder = placeholderText,
                placeholderStyle = placeholderStyle,
                imeAction = imeAction,
                keyboardType = keyboardType,
                roundedCorners = roundedCornersSize,
                maxLines = maxLines,
                fieldHeight = fieldHeight,
                onValueChanged = {
                    fieldValue = it
                    onValueChanged(it)
                },
                placeholderColor = placeholderColor,
                containerColor = containerColor,
                textColor = textColor,
                visualTransformation = visualTransformation
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnabledTextField(
    value: String,
    placeholder: String,
    placeholderStyle: TextStyle,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    roundedCorners: Dp,
    maxLines: Int,
    fieldHeight: Int,
    onValueChanged: (String) -> Unit,
    placeholderColor: Color,
    containerColor: Color,
    textColor: Color,
    onKeyboardAction: (Action) -> Unit,
    visualTransformation: VisualTransformation
) {
    TextField(
        value = value,
        placeholder = {
            EnabledText(
                text = placeholder,
                style = placeholderStyle,
                modifier = Modifier.fillMaxWidth(),
                labelColor = placeholderColor
            )
        },
        shape = RoundedCornerShape(roundedCorners),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = containerColor,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledLabelColor = Gray,
            disabledPlaceholderColor = TextColor_GRAY,
            disabledTextColor = TextColor_GRAY,
            cursorColor = Gray
        ),
        maxLines = maxLines,
        onValueChange = { onValueChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .height(height = fieldHeight.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        textStyle = if (fieldHeight > 48) {
            Typography.headlineLarge.copy(color = textColor)
        } else {
            Typography.bodyMedium.copy(color = textColor)
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onKeyboardAction(Action.Search)
            },
            onDone = {
                onKeyboardAction(Action.Done)
            },
            onGo = {
                onKeyboardAction(Action.Go)
            },
            onNext = {
                onKeyboardAction(Action.Next)
            },
            onPrevious = {
                onKeyboardAction(Action.Previous)
            },
            onSend = {
                onKeyboardAction(Action.Send)
            }
        ),
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisabledTextField(
    value: String,
    placeholder: String,
    placeholderStyle: TextStyle,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    roundedCorners: Dp,
    maxLines: Int,
    fieldHeight: Int,
    onValueChanged: (String) -> Unit,
    placeholderColor: Color,
    containerColor: Color,
    textColor: Color,
    visualTransformation: VisualTransformation
) {
    TextField(
        value = value,
        placeholder = {
            DisabledText(
                text = placeholder,
                style = placeholderStyle,
                modifier = Modifier.fillMaxWidth(),
                color = placeholderColor
            )
        },
        shape = RoundedCornerShape(roundedCorners),
        colors = TextFieldDefaults.colors(
            disabledTextColor = TextColor_GRAY,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            cursorColor = Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledLabelColor = TextColor_GRAY,
            disabledPlaceholderColor = TextColor_GRAY,
        ),
        onValueChange = { onValueChanged(it) },
        enabled = false,
        maxLines = maxLines,
        modifier = Modifier
            .fillMaxWidth()
            .height(fieldHeight.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        textStyle = if (fieldHeight > 48) {
            Typography.bodyMedium.copy(color = textColor)
        } else {
            Typography.headlineLarge.copy(color = textColor)
        },
        visualTransformation = visualTransformation
    )
}

@Composable
private fun EnabledText(
    text: String,
    style: TextStyle,
    modifier: Modifier,
    labelColor: Color

) {
    Text(
        text = text,
        style = style,
        color = labelColor,
        modifier = modifier
    )
}

@Composable
private fun DisabledText(
    text: String,
    style: TextStyle,
    modifier: Modifier,
    color: Color?
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color ?: Gray
    )
}

sealed interface Action {
    object Search : Action
    object Done : Action
    object Go : Action
    object Next : Action
    object Previous : Action
    object Send : Action
}