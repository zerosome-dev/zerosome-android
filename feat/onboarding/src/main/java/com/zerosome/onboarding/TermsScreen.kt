package com.zerosome.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zerosome.design.extension.ChangeSystemColor
import com.zerosome.design.ui.component.ZSAppBar
import com.zerosome.design.ui.component.ZSButton
import com.zerosome.design.ui.component.ZSScreen
import com.zerosome.design.ui.theme.Body1
import com.zerosome.design.ui.theme.Body2
import com.zerosome.design.ui.theme.H2
import com.zerosome.design.ui.theme.Label2
import com.zerosome.design.ui.theme.ZSColor
import com.zerosome.design.ui.view.CommonTitleView

@Composable
internal fun TermsScreen(
    onBackPressed: () -> Unit,
    onTermsAgreed: (isMarketingAgreed: Boolean) -> Unit,
    viewModel: TermsViewModel = hiltViewModel()
) {
    ZSScreen(modifier = Modifier.fillMaxSize()) {
        ChangeSystemColor(
            statusBarColor = Color.Transparent
        )
        ZSAppBar(
            navTitle = "",
            backNavigationIcon = painterResource(id = com.zerosome.design.R.drawable.ic_chevron_left),
            onBackPressed = onBackPressed
        )

        CommonTitleView(
            titleRes = R.string.screen_terms_title,
            descriptionRes = R.string.screen_terms_description
        )

        Spacer(modifier = Modifier.height(30.dp))

        AllAgreementComponent(
            componentTitle = R.string.screen_terms_accept_all,
            componentDescription = R.string.screen_terms_accept_all_description,
            isChecked = viewModel.uiState.allChecked
        ) {
            viewModel.setAction(TermsAction.ClickAll)
        }

        Spacer(modifier = Modifier.height(18.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp), thickness = 1.dp, color = ZSColor.Neutral100
        )

        Spacer(modifier = Modifier.height(20.dp))
        TermsAgreementComponent(
            componentTitle = R.string.screen_terms_context_service,
            isChecked = viewModel.uiState.serviceTermsAgreed,
            onCheckedChange = { viewModel.setAction(TermsAction.ClickService) },
            onClickedWatchPage = {})
        TermsAgreementComponent(
            componentTitle = R.string.screen_terms_context_privacy,
            isChecked = viewModel.uiState.privacyTermsAgreed,
            onCheckedChange = { viewModel.setAction(TermsAction.ClickPrivacy) },
            onClickedWatchPage = {})
        TermsAgreementComponent(
            componentTitle = R.string.screen_terms_context_marketing,
            isChecked = viewModel.uiState.marketingTermsAgreed,
            onCheckedChange = { viewModel.setAction(TermsAction.ClickMarketing) },
            onClickedWatchPage = {})

        Spacer(modifier = Modifier.weight(1f))

        ZSButton(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(bottom = 10.dp),
            enable = viewModel.uiState.canGoNext,
            onClick = {
                onTermsAgreed(viewModel.uiState.marketingTermsAgreed)
            }) {
            Text(stringResource(id = com.zerosome.design.R.string.common_next), style = Label2)
        }
    }
}

@Composable
private fun AllAgreementComponent(
    @StringRes componentTitle: Int,
    @StringRes componentDescription: Int,
    isChecked: Boolean,
    onCheckedChange: () -> Unit
) {
    val checkIconBackground by rememberUpdatedState(newValue = if (isChecked) ZSColor.Primary else ZSColor.Neutral200)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .clickable { onCheckedChange() }) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(id = com.zerosome.design.R.drawable.ic_check),
                contentDescription = "CHECK",
                modifier = Modifier.background(color = checkIconBackground, shape = CircleShape),
                tint = Color.White
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = componentTitle),
                    modifier = Modifier.fillMaxWidth(),
                    style = H2
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(id = componentDescription),
                    modifier = Modifier.fillMaxWidth(),
                    style = Body2,
                    color = ZSColor.Neutral500
                )
            }
        }
    }
}

@Composable
fun TermsAgreementComponent(
    @StringRes componentTitle: Int,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    onClickedWatchPage: () -> Unit
) {
    val checkIconBackground by rememberUpdatedState(newValue = if (isChecked) ZSColor.Primary else ZSColor.Neutral200)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(),
                role = Role.Checkbox,
                onClick = { onCheckedChange() })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = com.zerosome.design.R.drawable.ic_check),
                contentDescription = "CHECK",
                modifier = Modifier.background(color = checkIconBackground, shape = CircleShape),
                tint = Color.White
            )
            Text(
                text = stringResource(id = componentTitle),
                modifier = Modifier.weight(1f),
                style = Body1,
                color = Color.Black
            )
            Text(
                text = stringResource(id = R.string.screen_terms_watch_page),
                style = Body2,
                color = ZSColor.Neutral400,
                modifier = Modifier.clickable(interactionSource = remember {
                    MutableInteractionSource()
                }, indication = rememberRipple(), role = Role.Button, onClick = onClickedWatchPage)
            )
        }
    }
}