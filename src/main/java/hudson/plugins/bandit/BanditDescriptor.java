package hudson.plugins.bandit;

import hudson.maven.MavenModuleSet;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.plugins.analysis.core.PluginDescriptor;

import net.sf.json.JSONObject;

import org.apache.maven.project.MavenProject;
import org.kohsuke.stapler.StaplerRequest;

public final class BanditDescriptor extends PluginDescriptor {
	private static final long serialVersionUID = 1007L;

    /** Plug-in name. */
    private static final String PLUGIN_NAME = "bandit";
    /** Icon to use for the result and project action. */
    private static final String ACTION_ICON = "/plugin/bandit/icons/warnings-24x24.png";

    /**
     * Instantiates a new find bugs descriptor.
     */
    BanditDescriptor() {
        super(BanditPublisher.class);
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return Messages.Bandit_Publisher_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getIconUrl() {
        return ACTION_ICON;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        
        return FreeStyleProject.class.isAssignableFrom(jobType);
    }

}
