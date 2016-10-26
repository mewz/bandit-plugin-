package hudson.plugins.bandit;

import hudson.plugins.analysis.core.AbstractHealthDescriptor;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.util.model.AnnotationProvider;

import org.jvnet.localizer.Localizable;

public class BanditHealthDescriptor extends AbstractHealthDescriptor {
    /** Unique ID of this class. */
    private static final long serialVersionUID = 1001L;

    /**
     * Creates a new instance of {@link BanditHealthDescriptor} based on the
     * values of the specified descriptor.
     *
     * @param healthDescriptor the descriptor to copy the values from
     */
    public BanditHealthDescriptor(final HealthDescriptor healthDescriptor) {
        super(healthDescriptor);
    }

    /** {@inheritDoc} */
    @Override
    protected Localizable createDescription(final AnnotationProvider result) {
        if (result.getNumberOfAnnotations() == 0) {
            return Messages._Bandit_ResultAction_HealthReportNoItem();
        }
        else if (result.getNumberOfAnnotations() == 1) {
            return Messages._Bandit_ResultAction_HealthReportSingleItem();
        }
        else {
            return Messages._Bandit_ResultAction_HealthReportMultipleItem(result.getNumberOfAnnotations());
        }
    }
}

