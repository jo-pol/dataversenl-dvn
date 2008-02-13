/*
 * Dataverse Network - A web application to distribute, share and analyze quantitative data.
 * Copyright (C) 2007
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses
 * or write to the Free Software Foundation,Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

/*
 * AddAccountPage.java
 *
 * Created on October 4, 2006, 1:04 PM
 */
package edu.harvard.hmdc.vdcnet.web.login;

import edu.harvard.hmdc.vdcnet.admin.RoleRequestServiceLocal;
import edu.harvard.hmdc.vdcnet.admin.RoleServiceLocal;
import edu.harvard.hmdc.vdcnet.admin.UserServiceLocal;
import edu.harvard.hmdc.vdcnet.admin.VDCRole;
import edu.harvard.hmdc.vdcnet.admin.VDCUser;
import edu.harvard.hmdc.vdcnet.mail.MailServiceLocal;
import edu.harvard.hmdc.vdcnet.vdc.VDCServiceLocal;
import edu.harvard.hmdc.vdcnet.web.common.VDCBaseBean;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlInputHidden;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class ContributorRequestPage extends VDCBaseBean implements java.io.Serializable  {
    @EJB RoleRequestServiceLocal roleRequestService;
    @EJB UserServiceLocal userService;
    @EJB RoleServiceLocal roleService;
    @EJB VDCServiceLocal vdcService;
    @EJB MailServiceLocal mailService;
    
    
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public ContributorRequestPage() {
    }
    
    
    
    /**
     * <p>Callback method that is called whenever a page is navigated to,
     * either directly via a URL, or indirectly via page navigation.
     * Customize this method to acquire resources that will be needed
     * for event handlers and lifecycle methods, whether or not this
     * page is performing post back processing.</p>
     *
     * <p>Note that, if the current request is a postback, the property
     * values of the components do <strong>not</strong> represent any
     * values submitted with this request.  Instead, they represent the
     * property values that were saved for this view when it was rendered.</p>
     */
    public void init() {
        super.init();
        if (getVDCSessionBean().getLoginBean()!=null) {
            userId = getVDCSessionBean().getLoginBean().getUser().getId();
        } else {
            userId =(Long)getRequestMap().get("userId");
        }
        if (userId==null && hiddenUserId!=null ) {
            userId = (Long)hiddenUserId.getValue();
        }
        if (userId!=null && getVDCRequestBean().getCurrentVDCId()!=null && roleRequestService.findContributorRequest(userId, getVDCRequestBean().getCurrentVDCId())!=null) {
            alreadyRequested=true;
            
            
        }
    }
    
    /**
     * <p>Callback method that is called after the component tree has been
     * restored, but before any event processing takes place.  This method
     * will <strong>only</strong> be called on a postback request that
     * is processing a form submit.  Customize this method to allocate
     * resources that will be required in your event handlers.</p>
     */
    public void preprocess() {
    }
    
    /**
     * <p>Callback method that is called just before rendering takes place.
     * This method will <strong>only</strong> be called for the page that
     * will actually be rendered (and not, for example, on a page that
     * handled a postback and then navigated to a different page).  Customize
     * this method to allocate resources that will be required for rendering
     * this page.</p>
     */
    public void prerender() {
    }
    
    /**
     * Holds value of property userId.
     */
    private Long userId;
    
    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public Long getUserId() {
        return this.userId;
    }
    
    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    
    
    
    
    
    
    /**
     * Holds value of property alreadyRequested.
     */
    private boolean alreadyRequested;
    
    /**
     * Getter for property alreadyRequested.
     * @return Value of property alreadyRequested.
     */
    public boolean isAlreadyRequested() {
        return this.alreadyRequested;
    }
    
    /**
     * Setter for property alreadyRequested.
     * @param alreadyRequested New value of property alreadyRequested.
     */
    public void setAlreadyRequested(boolean alreadyRequested) {
        this.alreadyRequested = alreadyRequested;
    }
    
    
    
    public String generateRequest() {
        
        VDCUser user = userService.find(userId);
        vdcService.addContributorRequest(getVDCRequestBean().getCurrentVDCId(),userId);
        
        mailService.sendContributorRequestNotification(getVDCRequestBean().getCurrentVDC().getContactEmail(),
                user.getUserName(), getVDCRequestBean().getCurrentVDC().getName());
        
        // Send confirmation to user
        mailService.sendContributorRequestConfirmation(user.getEmail(),
                getVDCRequestBean().getCurrentVDC().getName());
        return "success";
        
    }
    
    /**
     * Holds value of property hiddenUserId.
     */
    private HtmlInputHidden hiddenUserId;
    
    /**
     * Getter for property hiddenUserId.
     * @return Value of property hiddenUserId.
     */
    public HtmlInputHidden getHiddenUserId() {
        return this.hiddenUserId;
    }
    
    /**
     * Setter for property hiddenUserId.
     * @param hiddenUserId New value of property hiddenUserId.
     */
    public void setHiddenUserId(HtmlInputHidden hiddenUserId) {
        this.hiddenUserId = hiddenUserId;
    }
    
    
    
}

