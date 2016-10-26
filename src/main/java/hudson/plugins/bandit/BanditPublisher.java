package hudson.plugins.bandit;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.Run;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Result;
import hudson.plugins.analysis.core.*;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.analysis.util.model.Priority;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;


public class BanditPublisher extends HealthAwarePublisher {

	/** Unique ID of this class. */
	private static final long serialVersionUID = 1003L;
	/** Descriptor of this publisher. */
	@Extension
	public static final BanditDescriptor BANDIT_DESCRIPTOR = new BanditDescriptor();
	public String outputFile;

	/**
	 * Creates a new instance of <code>BanditPublisher</code>
	 *
	 * @param outputFile
	 *        Workspace path to Bandit output
     */
	@DataBoundConstructor
	public BanditPublisher(final String outputFile) {
		super("BANDIT");
		this.setDefaultEncoding("UTF-8");
		this.outputFile = outputFile;
	}

	/**
	 * Creates a new instance of <code>BanditPublisher</code>.
	 *
	 * @deprecated prefer setters from the base class
	 */
	// CHECKSTYLE:OFF
	@SuppressWarnings("PMD.ExcessiveParameterList")
		@Deprecated
		public BanditPublisher(final String healthy, final String unHealthy, final String thresholdLimit,
				final boolean useDeltaValues,
				final String unstableTotalAll, final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
				final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal, final String unstableNewLow,
				final String failedTotalAll, final String failedTotalHigh, final String failedTotalNormal, final String failedTotalLow,
				final String failedNewAll, final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
				final boolean canRunOnFailed, final boolean shouldDetectModules, final boolean canComputeNew, final String outputFile) {
			super(healthy, unHealthy, thresholdLimit, "UTF-8", useDeltaValues,
					unstableTotalAll, unstableTotalHigh, unstableTotalNormal, unstableTotalLow,
					unstableNewAll, unstableNewHigh, unstableNewNormal, unstableNewLow,
					failedTotalAll, failedTotalHigh, failedTotalNormal, failedTotalLow,
					failedNewAll, failedNewHigh, failedNewNormal, failedNewLow,
					canRunOnFailed, shouldDetectModules, canComputeNew, "BANDIT");

			this.outputFile = outputFile;
		}
	// CHECKSTYLE:ON

	/**
	 * Creates a new parser set for old versions of this class.
	 *
	 * @return this
	 */
	@Override
		protected Object readResolve() {
			super.readResolve();
			return this;
		}

	/** {@inheritDoc} */
	@Override
		public Action getProjectAction(final AbstractProject<?, ?> project) {
			return new BanditProjectAction(project);
		}

	/** {@inheritDoc} */
	@Override
		public BuildResult perform(final Run<?, ?> build, final FilePath workspace, final PluginLogger logger) throws InterruptedException, IOException {
			return publishReport(build, workspace);
		}

		public BuildResult publishReport(final Run<?, ?> build, final FilePath workspace) throws InterruptedException, IOException {
			FilePath banditOutput = new FilePath(workspace, this.outputFile);
			String output = banditOutput.readToString();

			ParserResult project = new ParserResult(workspace);
			
			this.parse_results(output, project);

			BanditResult result = new BanditResult(build, getDefaultEncoding(), project, usePreviousBuildAsReference(), useOnlyStableBuildsAsReference());
			build.addAction(new BanditResultAction(build, this, result));

			return result;
		}
		
		
	    private void parse_results(String output, ParserResult project){
	        JSONObject json = new JSONObject(output);
                
	        JSONArray results = json.getJSONArray("results");
	        if(results != null){
	            for(int i = 0; i < results.length(); i ++){
	                JSONObject result = results.getJSONObject(i);
	                Priority priority = Priority.HIGH;

	                if(result.getString("issue_severity").equals("MEDIUM")){
	                    priority = Priority.NORMAL;
	                }
	                else if(result.getString("issue_severity").equals("LOW")){
	                    priority = Priority.LOW;
	                }

	                JSONArray lineRange = result.getJSONArray("line_range");
	                String issue = result.getString("issue_text").replaceAll("\\n", "<br>");
	                String issueText = "<p>Recommendation: " + issue + "</p>";
	                BanditWarning warning = new BanditWarning(
	                        result.getString("filename"),
	                        lineRange.getInt(0),
	                        lineRange.getInt(lineRange.length() - 1),
	                        result.getString("test_name"),
	                        null,
	                        issueText,
	                        priority
	                );
	                project.addAnnotation(warning);
	            }
	        }
	    }

	/** {@inheritDoc} */
	@Override
		public PluginDescriptor getDescriptor() {
			return BANDIT_DESCRIPTOR;
		}

	/** {@inheritDoc} */
	@Override
		protected boolean canContinue(final Result result) {
			return super.canContinue(result);
		}

  public hudson.matrix.MatrixAggregator createAggregator(hudson.matrix.MatrixBuild build,hudson.Launcher launcher,hudson.model.BuildListener listener) {
    return null;
  }
}
