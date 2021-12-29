import com.atlassian.confluence.api.model.Expansion
import com.atlassian.confluence.api.model.content.ContentType
import com.atlassian.confluence.api.service.content.ContentService
import com.atlassian.confluence.api.service.content.SpaceService
import com.onresolve.scriptrunner.runner.ScriptRunnerImpl

def contentService = ScriptRunnerImpl.getPluginComponent(ContentService)
def spaceService = ScriptRunnerImpl.getPluginComponent(SpaceService)

def title ='Welcome To Confluence'

def space = spaceService.find(new Expansion('name'))
    .withKeys('ds')
    .fetch()
    .get()

def findMatchingPage = contentService.find(new Expansion('version'), new Expansion('id'))
    .withType(ContentType.PAGE)
    .withSpace(space)
    .withTitle(title)
    .fetch()

def page = findMatchingPage.get()
def pageId = page.id
def pageVersion = page.version

log.debug("Page searched: ${title}")
log.debug("Retrieved page ID is: ${pageId}")
log.debug("Current page version is: ${pageVersion}")
