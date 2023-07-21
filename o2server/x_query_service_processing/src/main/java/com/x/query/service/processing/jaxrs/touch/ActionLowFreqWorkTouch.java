package com.x.query.service.processing.jaxrs.touch;

import com.x.base.core.project.Application;
import com.x.base.core.project.Applications;
import com.x.base.core.project.connection.CipherConnectionAction;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WrapBoolean;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.query.service.processing.schedule.LowFreqWork;

import io.swagger.v3.oas.annotations.media.Schema;

class ActionLowFreqWorkTouch extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionLowFreqWorkTouch.class);

    ActionResult<Wo> execute(EffectivePerson effectivePerson, String node) throws Exception {

        LOGGER.info("execute:{}.", effectivePerson::getDistinguishedName);
        ActionResult<Wo> result = new ActionResult<>();
        Wo wo = new Wo();
        wo.setValue(false);
        for (Application application : listApplication(node)) {
            String url = application.getUrlJaxrsRoot() + Applications.joinQueryUri("fireschedule", "classname",
                    LowFreqWork.class.getName());
            CipherConnectionAction.get(false, url);
            wo.setValue(true);
        }
        result.setData(wo);
        return result;
    }

    @Schema(name = "com.x.query.service.processing.jaxrs.touch.ActionLowFreqWorkTouch$Wo")
    public static class Wo extends WrapBoolean {

        private static final long serialVersionUID = -6264750611455675968L;

    }

}