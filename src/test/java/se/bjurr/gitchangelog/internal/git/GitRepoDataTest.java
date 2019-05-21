package se.bjurr.gitchangelog.internal.git;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import se.bjurr.gitchangelog.internal.git.model.GitTag;

public class GitRepoDataTest {

  public GitRepoData newGitRepoData(String originUrl) {
    List<GitTag> gitTags = new ArrayList<>();
    return new GitRepoData(originUrl, gitTags);
  }

  @Test
  public void testGitHubRepo() {
    GitRepoData sut = newGitRepoData("git@github.com:tomasbjerre/git-changelog-lib.git");

    assertThat(sut.findOwnerName().orNull()) //
        .isEqualTo("tomasbjerre");
    assertThat(sut.findRepoName().orNull()) //
        .isEqualTo("git-changelog-lib");

    assertThat(sut.findGitHubApi().orNull()) //
        .isEqualTo("https://api.github.com/repos/tomasbjerre/git-changelog-lib");
    assertThat(sut.findGitLabServer().orNull()) //
        .isEqualTo(null);
  }

  @Test
  public void testGitHubRepoTwoDots() {
    GitRepoData sut = newGitRepoData("git@github.com:tomasbjerre/git-changelog-lib-1.0.git");

    assertThat(sut.findOwnerName().orNull()) //
        .isEqualTo("tomasbjerre");
    assertThat(sut.findRepoName().orNull()) //
        .isEqualTo("git-changelog-lib-1.0");

    assertThat(sut.findGitHubApi().orNull()) //
        .isEqualTo("https://api.github.com/repos/tomasbjerre/git-changelog-lib-1.0");
    assertThat(sut.findGitLabServer().orNull()) //
        .isEqualTo(null);
  }

  @Test
  public void testGitHubRepoNoDotGit() {
    GitRepoData sut = newGitRepoData("https://github.com/VoltzEngine-Project/Build");

    assertThat(sut.findOwnerName().orNull()) //
        .isEqualTo("VoltzEngine-Project");
    assertThat(sut.findRepoName().orNull()) //
        .isEqualTo("Build");

    assertThat(sut.findGitHubApi().orNull()) //
        .isEqualTo("https://api.github.com/repos/VoltzEngine-Project/Build");
    assertThat(sut.findGitLabServer().orNull()) //
        .isEqualTo(null);
  }

  @Test
  public void testGitBitbucketRepo() {
    GitRepoData sut =
        newGitRepoData("https://tomasbjerre@bitbucket.org/ljohansson/grunt-connect-apimock.git");

    assertThat(sut.findOwnerName().orNull()) //
        .isEqualTo("ljohansson");
    assertThat(sut.findRepoName().orNull()) //
        .isEqualTo("grunt-connect-apimock");

    assertThat(sut.findGitHubApi().orNull()) //
        .isEqualTo(null);
    assertThat(sut.findGitLabServer().orNull()) //
        .isEqualTo(null);
  }

  @Test
  public void testGitLabRepo() {
    GitRepoData sut = newGitRepoData("http://root@gitlab.com/root/violations-test.git");

    assertThat(sut.findOwnerName().orNull()) //
        .isEqualTo("root");
    assertThat(sut.findRepoName().orNull()) //
        .isEqualTo("violations-test");

    assertThat(sut.findGitHubApi().orNull()) //
        .isEqualTo(null);
    assertThat(sut.findGitLabServer().orNull()) //
        .isEqualTo("https://gitlab.com/");
  }

  @Test
  public void testGitLabSubgroupsRepo() {
    GitRepoData sut =
        newGitRepoData("http://root@gitlab.com/root/subgroup1/subgroup2/violations-test.git");

    assertThat(sut.findOwnerName().orNull()) //
        .isEqualTo("root");
    assertThat(sut.findRepoName().orNull()) //
        .isEqualTo("subgroup1/subgroup2/violations-test");

    assertThat(sut.findGitHubApi().orNull()) //
        .isEqualTo(null);
    assertThat(sut.findGitLabServer().orNull()) //
        .isEqualTo("https://gitlab.com/");
  }
}
