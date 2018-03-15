package com.johannesqvarford.server;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.eclipse.jgit.api.Status;

@RestController
@RequestMapping("/git-is-dirty")
public class GitIsDirtyController
{
    @PostMapping
    @ResponseBody
    public Response invoke(@RequestBody String[] args, @RequestParam("workingDirectory") String workingDirectory)
    {
        try {
            GitIsDirtyOptions options = Options.parse(args, GitIsDirtyOptions.class);
            try (
                Repository repository = new RepositoryBuilder()
                    .findGitDir(new File(workingDirectory)).build();
                Git git = Git.wrap(repository))
            {
                Status status = git.status().call();
                return Response.ofExitStatus(status.isClean() ? 1 : 0);
            }
        }
        catch (Exception e)
        {
            return Response.exception(e);
        }
    }


}