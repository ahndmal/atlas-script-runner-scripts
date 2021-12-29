package com.bh.test.macro;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.confluence.api.service.content.ContentService;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.content.service.PageService;
import com.atlassian.confluence.content.service.SpaceService;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.labels.SpaceLabelManager;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.AttachmentManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.confluence.xhtml.api.XhtmlContent;
import com.atlassian.sal.api.component.ComponentLocator;
import com.atlassian.user.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpaceMacro implements Macro {

    private final String TEMPLATE = "templates/space-macro.vm";

    private final PageManager pageManager;
    private final XhtmlContent xhtmlContent;
    private final UserAccessor userAccessor;
    private final UserManager userManager;
    private final SpaceManager spaceManager;
    private final ContentService contentService;
    private final LabelManager labelManager;
    private final PageService pageService;
    private final SpaceService spaceService;
    private final ActiveObjects activeObjects;
    private final AttachmentManager attachmentManager;
    private final SpaceLabelManager spaceLabelManager;

    public SpaceMacro(PageManager pageManager, XhtmlContent xhtmlContent, UserAccessor userAccessor, UserManager userManager,
                      SpaceManager spaceManager, ContentService contentService, LabelManager labelManager,
                      PageService pageService,
                      SpaceService spaceService,
                      ActiveObjects activeObjects,
//                     SettingsManager settingsManager
//                     GlobalSettingsManager globalSettingsManager
                      AttachmentManager attachmentManager,
                      SpaceLabelManager spaceLabelManager) {
        this.pageManager = pageManager;
        this.xhtmlContent = xhtmlContent;
        this.userAccessor = userAccessor;
        this.userManager = userManager;
        this.spaceManager = spaceManager;
        this.contentService = contentService;
        this.labelManager = labelManager;
        this.pageService = pageService;
        this.spaceService = spaceService;
        this.activeObjects = activeObjects;
//        this.settingsManager = settingsManager;
//        this.globalSettingsManager = globalSettingsManager;
        this.attachmentManager = attachmentManager;
        this.spaceLabelManager = spaceLabelManager;
    }

    @Override
    public String execute(Map<String, String> map, String body, ConversionContext conversionContext) throws MacroExecutionException {

        Map<String, Object> context = MacroUtils.defaultVelocityContext();
//        VelocityContext contextMap = new VelocityContext(MacroUtils.defaultVelocityContext());

//        ComponentLocator.getComponent()

        // SpaceLabelManager
        List<String> spaceLabels = new ArrayList<>();
        spaceLabels = spaceManager.getAllSpaces().stream().filter(s -> {
            List labels = spaceLabelManager.getTeamLabelsOnSpace(s.getKey());
            return labels.contains("team_work");
        }).filter(s -> !s.isPersonal())
                .map(s -> s.getName())
                .collect(Collectors.toList());

        context.put("spaceLabels", spaceLabels);

        return VelocityUtils.getRenderedTemplate(TEMPLATE, context);
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}
