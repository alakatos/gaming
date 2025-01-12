package hu.laki.lights.model;

import java.awt.geom.Point2D;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MassPoint {
    public enum AttenuationType {
        AMBIENT,
        LINEAR,
        QUADRATIC, QBIC
    }
    @Setter
    @Builder.Default
    private Point2D location = new Point2D.Double(0,0);
    @Builder.Default
    private double mass = 0;
    @Builder.Default
    private double zeroGravityRadius = 10;
    @Builder.Default
    private AttenuationType attenuationType = AttenuationType.QUADRATIC;
}
