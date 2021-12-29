import com.atlassian.confluence.spaces.SpaceManager
import com.atlassian.confluence.pages.DraftManager
import com.atlassian.sal.api.component.ComponentLocator
import com.atlassian.confluence.api.model.Expansion;
import com.atlassian.confluence.api.model.Expansions;
import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentStatus;
import com.atlassian.confluence.api.model.content.ContentType
import com.atlassian.confluence.api.service.content.ContentService
import com.atlassian.confluence.api.service.content.SpaceService
import com.atlassian.confluence.api.model.content.SpaceType;
import com.atlassian.confluence.api.model.content.id.ContentId;
import com.atlassian.confluence.api.model.locator.ContentLocator;
import com.atlassian.confluence.api.model.pagination.SimplePageRequest;
import com.atlassian.confluence.api.service.content.ContentDraftService;
import com.atlassian.confluence.api.service.content.ContentService;
import com.onresolve.scriptrunner.runner.ScriptRunnerImpl


def contentService = ScriptRunnerImpl.getPluginComponent(ContentService)
def spaceService = ScriptRunnerImpl.getPluginComponent(SpaceService)

def spaceManager = ComponentLocator.getComponent(SpaceManager.class)
def draftManager = ComponentLocator.getComponent(DraftManager.class)

List<Content> results = contentService.find(new Expansion("version"), new Expansion("history"))
                .withType(ContentType.PAGE)
                .withStatus(ContentStatus.DRAFT)
                .fetchMany(ContentType.PAGE, new SimplePageRequest(1,100)).getResults();


return results

