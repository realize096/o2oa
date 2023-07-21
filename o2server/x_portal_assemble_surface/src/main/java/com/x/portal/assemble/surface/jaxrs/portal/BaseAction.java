package com.x.portal.assemble.surface.jaxrs.portal;

import com.x.base.core.project.cache.Cache;
import com.x.base.core.project.jaxrs.StandardJaxrsAction;
import com.x.portal.core.entity.Portal;

abstract class BaseAction extends StandardJaxrsAction {

	protected static final String DEFAULT_PORTAL_ICON_BASE64 = "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";

	Cache.CacheCategory cacheCategory = new Cache.CacheCategory(Portal.class);
}
