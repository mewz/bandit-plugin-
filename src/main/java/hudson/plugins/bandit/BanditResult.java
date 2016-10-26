package hudson.plugins.bandit;

import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.plugins.analysis.core.BuildHistory;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.core.ResultAction;

import com.thoughtworks.xstream.XStream;


public class BanditResult extends BuildResult {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 1004L;

    /**
     * Creates a new instance of {@link BanditResult}
     * @param build
     *            the current build as owner of this action
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param result
     *            the parsed result with all annotations
     * @param usePreviousBuildAsReference
     *            compare with previous build
     * @param useStableBuildAsReference
     *            compare with only stable builds
     */
    public BanditResult(final Run<?, ?> build, final String defaultEncoding, final ParserResult result,
                          final boolean usePreviousBuildAsReference, final boolean useStableBuildAsReference) {
        this(build, defaultEncoding, result, usePreviousBuildAsReference, useStableBuildAsReference,
                BanditResultAction.class);

    }

    protected BanditResult(final Run<?, ?> build, final String defaultEncoding, final ParserResult result,
                               final boolean usePreviousBuildAsReference, final boolean useStableBuildAsReference,
                               final Class<? extends ResultAction<BanditResult>> actionType) {
        this(build, new BuildHistory(build, actionType, usePreviousBuildAsReference, useStableBuildAsReference),
                result, defaultEncoding, true);
    }

    private BanditResult(final Run<?, ?> build, final BuildHistory history,
                     final ParserResult result, final String defaultEncoding, final boolean canSerialize) {
        super(build, history, result, defaultEncoding);

        if (canSerialize) {
            serializeAnnotations(result.getAnnotations());
        }
    }


    /** {@inheritDoc} */
    @Override
    protected void configure(final XStream xstream) {
        xstream.alias("warning", BanditWarning.class);
    }

    /**
     * Returns a summary message for the summary.jelly file.
     *
     * @return the summary message
     */
    public String getSummary() {
        //return ResultSummary.createSummary(this);
		return this.createSummary();
    }

    /** {@inheritDoc} */
    @Override
    protected String createDeltaMessage() {
        //return ResultSummary.createDeltaMessage(this);
        StringBuilder summary = new StringBuilder();
        
        if(this.getNumberOfNewWarnings() > 0){
            summary.append("<li><a href='banditResult/new'>");
            if(this.getNumberOfNewWarnings() == 1){
                summary.append("1 new issue found");
            }
            else{
                summary.append(String.format("%d new issues found", this.getNumberOfNewWarnings()));
            }
            summary.append("</a></li>");
        }        
        
        if(this.getNumberOfFixedWarnings() > 0){
            summary.append("<li><a href=\"banditResult/fixed\">");
            if (this.getNumberOfFixedWarnings() == 1) {
                summary.append("1 fixed issue found");
            }
            else{
                summary.append(String.format("%d fixed issues found", this.getNumberOfFixedWarnings()));
            }
            summary.append("</li></a>");
        }
        
        return summary.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected String getSerializationFileName() {
        return "compiler-Bandit.xml";
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Bandit_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected Class<? extends ResultAction<? extends BuildResult>> getResultActionType() {
        return BanditResultAction.class;
    }
	
    private String createSummary(){
        StringBuilder summary = new StringBuilder();
        int bugs = this.getNumberOfAnnotations();
        
        summary.append("Bandit vulnerability results: ");
        if(bugs > 0){
            summary.append("<a href='banditResult'>");
        }
        if(bugs == 1){
            summary.append("1 issue found");
        }
        else{
            summary.append(String.format("%d issues found", bugs));
        }
        if(bugs > 0){
            summary.append("</a>");
        }
        return summary.toString();
    }
}
