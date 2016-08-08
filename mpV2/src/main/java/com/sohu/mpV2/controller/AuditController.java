package com.sohu.mpV2.controller;

//@Controller
//@RequestMapping("audit")
public class AuditController {

//    @Resource
//    private MpChannelService mpChannelService;
//
//    @Resource
//    private MpMediaProfileService mpMediaProfileService;
//
//    @Resource
//    private RelationNotifyService relationNotifyService;
//
//    @RequestMapping()
//    public ModelAndView audit(@RequestParam("pno") int pno, @RequestParam("psize") int psize) {
//        List<AuditProfile> list = new ArrayList<AuditProfile>();
//
//    }
//
//    @RequestMapping("add")
//    @ResponseBody
//    public String add(@RequestParam("mp_media_id") String mpMediaId, @RequestParam("username") String username, @RequestParam("mp_channel_id") int mpChannelId,
//            @RequestParam("from_where") int fromWhere, @RequestParam("pass") boolean pass, @RequestParam("audit_type") int auditType,
//            @RequestParam("copyRight") int copyRight, @RequestParam("avator_url") String avatorUrl) {
//        if (StringUtils.isEmpty(mpMediaId) || StringUtils.isEmpty(username)) {
//            return "";
//        }
//        MpMediaProfile mpMediaProfile = mpMediaProfileService.byMpMediaId(mpMediaId);
//        if (mpMediaProfile != null) {
//            return "";
//        }
//        MpChannel mpChannel = mpChannelService.byId(mpChannelId);
//        if (mpChannel == null) {
//            return "";
//        }
//        if (!FromWhere.exist(fromWhere)) {
//            return "";
//        }
//        if (auditType != 1 && auditType != 2) {
//            return "";
//        }
//        if (copyRight != 1 && copyRight != 0) {
//            return "";
//        }
//        mpMediaProfile = new MpMediaProfile();
//        mpMediaProfile.setAuditType(auditType);
//        mpMediaProfile.setAuditStatus(true);
//        mpMediaProfile.setChannelModified(0);
//        mpMediaProfile.setCmsMediaId(52720); // cms的媒体id
//        mpMediaProfile.setCopyRight(copyRight);
//        mpMediaProfile.setFromWhere(fromWhere);
//        mpMediaProfile.setGrab(false); // 需要定规则
//        mpMediaProfile.setGrade(60);
//        mpMediaProfile.setMediaType(2); // 2 = 自媒体
//        mpMediaProfile.setMpChannelId(mpChannelId);
//        mpMediaProfile.setMpMediaId(mpMediaId);
//        mpMediaProfile.setPass(pass);
//
//        Profile profile = new Profile();
//        profile.setAvatorUrl(avatorUrl);
//        profile.setDescription("");
//        profile.setPassport(mpMediaId);
//        profile.setUsername(username);
//        profile.setAccountType(0); // 0 = 自媒体
//        profile.setStatus(Profile.Status.PASS.getCode());
//        profile.setFromWhere(Profile.FromWhere.AUTOGEN.getCode()); // 暂时不定
//
//        ProfilePersonal pp = new ProfilePersonal();
//        pp.setPassport(mpMediaId);
//
//        relationNotifyService.notifyNewAccount(mpMediaId, mpId);
//
//        // 如果是直接同步通过的,异步处理后续事情
//        if (pass) {
//            final MpMediaProfile mediaProfile = mpMediaProfile;
//            new Thread() {
//                public void run() {
//                    try {
//                        handleSyncPassAccount(mediaProfile);
//                    } catch (Exception e) {
//                    }
//                }
//            }.start();
//        }
//    }
//
//    /**
//     * 处理同步的账号
//     */
//    private void handleSyncPassAccount(MpMediaProfile mediaProfile) {
//        // 先更新isohu url
//        ISohuHttpUtils.activate(mediaProfile.getMpMediaId());
//        String isohuUrl = ISohuHttpUtils.genIsohuUrl(mediaProfile.getMpMediaId());
//        if (StringUtils.isNotBlank(isohuUrl))
//            mediaProfile.setWeiboUrl(isohuUrl);
//        mpMediaProfileService.updateWeiboUrl(mediaProfile);
//
//        // 调用手搜接口，创建一个mp账号，用于ad
//        adFragCompileService.addAdToWap(mediaProfile.getId());
//        // 生成一个空的碎片,给wap端使用
//        adFragCompileService.createBlankAdFrag(mediaProfile.getId());
//
//        // 创建内容完整的mediaInfo碎片
//        MediaInfo mediaInfo = mediaInfoService.getByMpId(mediaProfile.getId());
//        mediaInfoService.updateMediaInfoFrag(mediaInfo, mediaProfile.getId());
//
//        boolean sendMail = false;
//        StringBuilder mailContent = new StringBuilder();
//        String mediaName = mediaInfo.getUserName();
//        String mediaLogo = mediaInfo.getLogoUrl();
//        String mediaDesc = mediaName;
//        Integer mediaType = mediaProfile.getMediaType();
//
//        if (Constants.MP_MEDIA_TYPE == mediaType)
//            mediaDesc = "[入驻媒体]" + mediaName;
//        else if (Constants.MP_SELFMEDIA_TYPE == mediaType)
//            mediaDesc = "[入驻自媒体]" + mediaName;
//        else if (Constants.MP_ENTERPRISE_TYPE == mediaType)
//            mediaDesc = "[入驻企业]" + mediaName;
//        else if (Constants.MP_GOVERNMENT_TYPE == mediaType)
//            mediaDesc = "[入驻政府]" + mediaName;
//
//        boolean createCmsMedia = true;
//        if (mediaProfile.getMpMediaId().startsWith("sohuzmt")) // 自动创建的账号,不进cms
//            createCmsMedia = false;
//
//        if (createCmsMedia && mediaName != null && !mediaName.equals("")) {// 创建媒体
//            HashMap<String, String> params = new HashMap<String, String>();
//            params.put("media_name", mediaName);
//            if (mediaDesc != null)
//                params.put("media_desc", mediaDesc);
//            if (mediaLogo != null)
//                params.put("media_logo", mediaLogo);
//            if (mediaProfile.getSiteUrl() != null)
//                params.put("media_url", mediaProfile.getSiteUrl());
//            log.info("prepare params:" + params.toString());
//            String r = HttpUtil.get(cmsMediaApiUrl, params, 30000, 10000);
//            try {
//                String res = r.trim();
//                log.info("return string:" + res);
//                JSONObject jo = JSONObject.fromObject(res);
//                if (jo.getBoolean("status")) {
//                    Integer id = jo.getInt("media_id");
//                    if ((id == null || id == 0)) {
//                        sendMail = true;
//                        mailContent.append("create cms media return id is not right,id=").append(id);
//                        log.info("create cms media return id is not right,id=" + id);
//                    } else {
//                        mediaProfile.setCmsMediaId(id);
//                        mediaProfileService.updateMpMediaProfile(mediaProfile);
//                    }
//
//                    log.info("create cms media success, media id :" + id);
//                } else {
//                    sendMail = true;
//                    mailContent.append("create cms media fail, msg:").append(jo.optString("msg", "no msg"));
//                    log.error("create cms media fail, msg:" + jo.optString("msg", "no msg"));
//                }
//            } catch (Exception e) {
//                sendMail = true;
//                mailContent.append("create cms media fail, ret=[" + r + "]");
//                log.error("create cms media fail, ret=[" + r + "]", e);
//            } finally {
//                String serverIp = IPUtils.getIpAddress();
//                if (serverIp.contains("10.16")) // 只有正式环境才发邮件
//                    sendMail = false;
//                if (sendMail) {
//                    String text = "passport:" + mediaProfile.getMpMediaId() + ",meidaName:" + mediaName + "\nInfo:" + mailContent.toString();
//                    emailService.sends(emailAddress, "创建CMS Media失败", text);
//                }
//            }
//        } else {
//            log.info("updateProfile failed, mediaName is not right,mediaName=" + mediaName);
//        }
//        log.info("create SyncPassAccount end for passport=" + mediaProfile.getMpMediaId() + ",next create kz");
//        siteService.filterCreateSite(mediaProfile);
//    }

}
