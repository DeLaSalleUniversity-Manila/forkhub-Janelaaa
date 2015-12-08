/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.janela.mobile.ui.gist;

import static android.app.Activity.RESULT_OK;
import static com.janela.mobile.RequestCodes.GIST_CREATE;
import static com.janela.mobile.RequestCodes.GIST_VIEW;
import android.content.Intent;

import com.janela.mobile.accounts.GitHubAccount;
import com.janela.mobile.core.ResourcePager;
import com.janela.mobile.core.gist.GistPager;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.PageIterator;

/**
 * Fragment to display a list of Gists
 */
public class MyGistsFragment extends GistsFragment {

    @Inject
    private Provider<GitHubAccount> accountProvider;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == GIST_CREATE || requestCode == GIST_VIEW)
                && RESULT_OK == resultCode) {
            forceRefresh();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected ResourcePager<Gist> createPager() {
        return new GistPager(store) {

            @Override
            public PageIterator<Gist> createIterator(int page, int size) {
                return service.pageGists(accountProvider.get().getUsername(),
                        page, size);
            }
        };
    }
}
