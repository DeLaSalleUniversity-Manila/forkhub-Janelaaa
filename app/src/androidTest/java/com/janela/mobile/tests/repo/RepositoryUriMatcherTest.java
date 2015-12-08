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
package com.janela.mobile.tests.repo;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.janela.mobile.core.repo.RepositoryUriMatcher;

import org.eclipse.egit.github.core.Repository;

/**
 * Unit tests of {@link RepositoryUriMatcher}
 */
public class RepositoryUriMatcherTest extends AndroidTestCase {

    /**
     * Verity empty uri
     */
    public void testEmptyUri() {
        assertNull(RepositoryUriMatcher.getRepository(Uri.parse("")));
    }

    /**
     * Verify URI with no owner
     */
    public void testUriWithNoOnwer() {
        assertNull(RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com")));
        assertNull(RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com/")));
        assertNull(RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com//")));
    }

    /**
     * Verify URI with owner but no name
     */
    public void testUriWithNoName() {
        assertNull(RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com/defunkt")));
        assertNull(RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com/defunkt/")));
    }

    /**
     * Verify URI with owner and name
     */
    public void testHttpUriWithOwnerAndName() {
        Repository repo = RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com/defunkt/resque"));
        assertNotNull(repo);
        assertEquals("resque", repo.getName());
        assertNotNull(repo.getOwner());
        assertEquals("defunkt", repo.getOwner().getLogin());
    }

    /**
     * Verify URI with owner and name
     */
    public void testHttpsUriWithOwnerAndName() {
        Repository repo = RepositoryUriMatcher.getRepository(Uri
                .parse("https://janela.com/mojombo/jekyll"));
        assertNotNull(repo);
        assertEquals("jekyll", repo.getName());
        assertNotNull(repo.getOwner());
        assertEquals("mojombo", repo.getOwner().getLogin());
    }

    /**
     * Verify URI with issues
     */
    public void testHttpsUriWithIssues() {
        Repository repo = RepositoryUriMatcher.getRepository(Uri
                .parse("https://janela.com/mojombo/jekyll/issues"));
        assertNotNull(repo);
        assertEquals("jekyll", repo.getName());
        assertNotNull(repo.getOwner());
        assertEquals("mojombo", repo.getOwner().getLogin());
    }

    /**
     * Verify URI with pulls
     */
    public void testHttpsUriWithPulls() {
        Repository repo = RepositoryUriMatcher.getRepository(Uri
                .parse("https://janela.com/mojombo/jekyll/pulls"));
        assertNotNull(repo);
        assertEquals("jekyll", repo.getName());
        assertNotNull(repo.getOwner());
        assertEquals("mojombo", repo.getOwner().getLogin());
    }

    /**
     * Verify URI with white-listed owner
     */
    public void testInvalidOwner() {
        assertNull(RepositoryUriMatcher.getRepository(Uri
                .parse("http://janela.com/blog/page1")));
    }
}