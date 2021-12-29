 int draftsCount = draftManager.countDrafts("admin");
        Draft draft = draftManager.getDraft(65542);
//        boolean isDraft = DraftUtils.isDraft(draft);

//        ContentEntityObject draft1 = draftsTransitionHelper.getDraft(65542);
//        DraftData draftData = new DraftData(String title, String content, String type, String spaceKey, int pageVersion, Long pageId, Long draftId, Long parentPageId, PagePermissionData permissions)

//        draftManager.findDraft(Long pageId, String owner, String type, String spaceKey)
        //ONE
        Optional<Content> optionalContent = contentService.find(new Expansion("PAGE"), new Expansion("history"))
                .withId(ContentId.of(65542))
                .fetch();

        //LOCATOR
        ContentLocator contentLocator = ContentLocator.builder().forPage().bySpaceKeyAndTitle("", "");

        // MANY
        List<Content> results = contentService.find(new Expansion("history"), new Expansion("version"))
                .withType(ContentType.PAGE)
                .withStatus(ContentStatus.DRAFT)
//                .withLocator(ContentLocator.builder().)
                .fetchMany(ContentType.PAGE, new SimplePageRequest(1,100)).getResults();

//        results.forEach(c -> {
//            contentDraftService.deleteDraft(c.getId());
//        });
