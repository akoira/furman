// DSPUtils.cpp : Implementation of CDSPUtils

#include "stdafx.h"
#include "DSPUtils.h"
#include "atlstr.h"


// CDSPUtils



AcApDocument* FindDocument(const TCHAR *DWGName)
{
	CString DocFileName;
	AcApDocumentIterator* DocIt=acDocManager->newAcApDocumentIterator();
	AcApDocument *pItDoc=NULL;

	if(DWGName!=NULL){
		for(;!DocIt->done();DocIt->step()){
			pItDoc=DocIt->document();
//Acad::ErrorStatus err=pItDoc->database()->getFilename(DocFileName);
			DocFileName=pItDoc->fileName();
			if( !DocFileName.IsEmpty() ){ 
				if( DocFileName.CompareNoCase(DWGName)==0 )
					break;
			}
			pItDoc=NULL;
		}
		delete DocIt;
	}
	return pItDoc;
}

STDMETHODIMP CDSPUtils::GetData(BSTR DWGFullPath, DOUBLE* LineLength, DOUBLE* CurveLength, DOUBLE* Square, LONG* RegionCount)
{
	*LineLength=0.0;
	*CurveLength=0.0;
	*Square=0.0;

	CString DWGName(DWGFullPath);
	AcApDocument* pItDoc=FindDocument(DWGName);
	Acad::ErrorStatus OpenErr;
	if( pItDoc==NULL ){
		OpenErr=acDocManager->appContextOpenDocument(DWGName);
		pItDoc=FindDocument(DWGName);
	}
	if( pItDoc==NULL ){
		CString ErrMsg;
		ErrMsg.Format(_T("Невозможно открыть документ %s"),DWGName);
		::MessageBox(NULL,ErrMsg,0,MB_SYSTEMMODAL+MB_ICONERROR+MB_OK);
		return S_OK;
	}
	OpenErr=acDocManager->activateDocument(pItDoc);
	

	AcDbBlockTable* pBT;
	OpenErr= pItDoc->database()->getBlockTable( pBT, AcDb::kForRead );

	AcDbBlockTableRecord* pBTR;
	OpenErr = pBT->getAt( ACDB_MODEL_SPACE, pBTR, AcDb::kForRead );
	pBT->close();

	AcDbVoidPtrArray curveSegments;
	AcDbBlockTableRecordIterator* pIT;
	OpenErr = pBTR->newIterator( pIT );
	for ( ; !pIT->done(); pIT->step()) {
		AcDbEntity *pEnt;
		AcDbObjectId id;

		if (Acad::eOk == pIT->getEntityId( id )) {
			if ( Acad::eOk == pIT->getEntity(pEnt, AcDb::kForRead)) {
				bool DoAddForRegion = false;
				AcDbCurve *pCurve=AcDbCurve::cast(pEnt);
				if( pCurve ){
					double Leng;
					double param;
					pCurve->getEndParam(param);
					pCurve->getDistAtParam( param, Leng );

					if( AcDbLine::cast(pEnt) ){
						*LineLength += Leng;
						DoAddForRegion = true;
					}
					if( AcDbCircle::cast(pEnt) ||
							AcDbArc::cast(pEnt) ||
							AcDbEllipse::cast(pEnt) ||
							AcDbSpline::cast(pEnt) ){
						*CurveLength += Leng;
						DoAddForRegion = true;
					}
					AcDbPolyline *pPoly = AcDbPolyline::cast(pEnt);
					if( pPoly ){
						DoAddForRegion = true;
						long nv = pPoly->numVerts();
						for( int i=0; i<nv; i++ ){
							AcGeLineSeg2d LineSeg;
							double bulge=1.0;
							OpenErr=pPoly->getBulgeAt(i,bulge);
							if( fabs(bulge) < 0.0000000001 ){
								if( Acad::eOk==pPoly->getLineSegAt( i, LineSeg ) ){
									double SegLeng = LineSeg.length();
									*LineLength += SegLeng;
									Leng-=SegLeng;
								}
							}
						}
						*CurveLength+=Leng;
					}
					
				}
				if( DoAddForRegion )
					curveSegments.append( pEnt );
				else
					pEnt->close();
			}
		}
	}
	delete pIT;
	pBTR->close();

	AcDbVoidPtrArray regions;
	AcDbRegion::createFromCurves( curveSegments, regions );
	*RegionCount = regions.logicalLength();
	for( int i=0; i<regions.logicalLength(); i++ ){
		AcDbRegion *pRegion = (AcDbRegion *)regions[i];
		double regionArea;
		pRegion->getArea(regionArea);
		*Square += regionArea;
		delete pRegion;
	}


	for( int i=0; i<curveSegments.logicalLength(); i++ ){
		AcDbEntity *pEnt = (AcDbEntity *)curveSegments[i];
		pEnt->close();
	}

	return S_OK;
}
