package com.zerosome.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.ui.component.ButtonSize
import com.zerosome.design.ui.component.ButtonType
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.component.ZSTextField
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.SubTitle1
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.domain.model.ReportReason

@Composable
internal fun ReportReasonScreen(
    onBackPressed: () -> Unit,
    reportViewModel: ReportViewModel = hiltViewModel(),
) {
    ZSScreen(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ZSAppBar(
            backNavigationIcon = painterResource(com.zerosome.design.R.drawable.ic_chevron_left),
            onBackPressed = onBackPressed,
            navTitle = "신고하기"
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Text(
                    "신고하는 이유가 무엇일까요?", style = H2, color = ZSColor.Neutral800, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(ReportReason.entries) {
                ReasonItemComponent(it.reasonName, reportViewModel.uiState.selectedReason == it) {
                    reportViewModel.setAction(ReportAction.ClickSelectReason(it))
                }
            }
            if (reportViewModel.uiState.selectedReason == ReportReason.ETC) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text("상세하게 작성해주세요.", style = H2, color = ZSColor.Neutral900, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.dp))
                    Spacer(Modifier.height(16.dp))
                    ZSTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        singleLine = false,
                        minLines = 3,
                        text = reportViewModel.uiState.reasonDescription,
                        onTextChanged = {
                            reportViewModel.setAction(
                                ReportAction.WriteReasonDescription(it)
                            )
                        },
                        placeHolderText = "이유를 입력해주세요."
                    )
                }
            }
        }
        ZSButton(
            onClick = { reportViewModel.setAction(ReportAction.ClickConfirmButton) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 10.dp),
            buttonSize = ButtonSize.LARGE,
            buttonType = ButtonType.PRIMARY,
            enable = reportViewModel.uiState.selectedReason != null || reportViewModel.uiState.reasonDescription.isNotEmpty()
        ) {
            Text(text = "신고하기", style = SubTitle1, color = Color.White)
        }
    }
}


@Composable
fun ReasonItemComponent(
    itemString: String,
    isSelected: Boolean,
    onItemSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 22.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                role = Role.DropdownList,
                onClick = onItemSelect
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isSelected) ZSColor.Primary else ZSColor.Neutral200,
                    shape = CircleShape
                )
        ) {
            Image(
                painterResource(com.zerosome.design.R.drawable.ic_check),
                contentDescription = "CHECK",
                colorFilter = ColorFilter.tint(color = Color.White)
            )
        }
        Text(
            text = itemString,
            modifier = Modifier.weight(1f),
            color = ZSColor.Neutral900,
            style = Body2
        )
    }
}