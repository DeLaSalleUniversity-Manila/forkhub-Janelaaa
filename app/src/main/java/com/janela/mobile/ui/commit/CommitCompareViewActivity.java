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
package com.janela.mobile.ui.commit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.janela.mobile.Intents.EXTRA_BASE;
import static com.janela.mobile.Intents.EXTRA_HEAD;
import static com.janela.mobile.Intents.EXTRA_REPOSITORY;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.janela.mobile.Intents.Builder;
import com.janela.mobile.R;
import com.janela.mobile.ui.DialogFragmentActivity;
import com.janela.mobile.ui.repo.RepositoryViewActivity;
import com.janela.mobile.util.AvatarLoader;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;

/**
 * Activity to display a comparison between two commits
 */
public class CommitCompareViewActivity extends DialogFragmentActivity {

    /**
     * Create intent for this activity
     *
     * @param repository
     * @param base
     * @param head
     * @return intent
     */
    public static Intent createIntent(final Repository repository,
            final String base, final String head) {
        Builder builder = new Builder("commits.compare.VIEW");
        builder.add(EXTRA_BASE, base);
        builder.add(EXTRA_HEAD, head);
        builder.repo(repository);
        return builder.toIntent();
    }

    private Repository repository;

    @Inject
    private AvatarLoader avatars;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = getSerializableExtra(EXTRA_REPOSITORY);

        setContentView(R.layout.commit_compare);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle(repository.generateId());
        avatars.bind(actionBar, repository.getOwner());

        fragment = getSupportFragmentManager()
                .findFragmentById(android.R.id.list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu optionsMenu) {
        if (fragment != null)
            fragment.onCreateOptionsMenu(optionsMenu, getMenuInflater());

        return super.onCreateOptionsMenu(optionsMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = RepositoryViewActivity.createIntent(repository);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        default:
            if (fragment != null)
                return fragment.onOptionsItemSelected(item);
            else
                return super.onOptionsItemSelected(item);
        }
    }
}
