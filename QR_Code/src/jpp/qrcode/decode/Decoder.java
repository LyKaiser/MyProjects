package jpp.qrcode.decode;

import jpp.qrcode.*;
import jpp.qrcode.encode.MaskSelector;
import jpp.qrcode.reedsolomon.ReedSolomonException;

public class Decoder {
	public static String decodeToString(QRCode qrCode) throws ReedSolomonException {
		boolean[][] data = qrCode.data();
		ErrorCorrection error = qrCode.errorCorrection();
		MaskPattern maskPattern = qrCode.maskPattern();
		Version version= qrCode.version();
		MaskFunction maFu = maskPattern.maskFunction();
		ReservedModulesMask mask = ReservedModulesMask.forVersion(version);
		MaskApplier.applyTo(data,maFu,mask);
		ErrorCorrectionInformation errInfo = version.correctionInformationFor(error);
		int byteCount = errInfo.totalByteCount();
		byte[] by = DataExtractor.extract(data,mask,byteCount);
		byte [] byDestr = DataDestructurer.destructure(by,errInfo);
		return DataDecoder.decodeToString(byDestr,version,error);

	}
}
