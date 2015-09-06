// App.cpp : Implementation of CApp

#include "stdafx.h"
#include "App.h"


// CApp

void CApp::InitAcad(void)
{
	HRESULT Res;
	if( pAcad==NULL ){
		CComPtr<IUnknown> pActiveAcad;
		Res = GetActiveObject(CLSID_AcadApplication,0,&pActiveAcad);
		if( pActiveAcad ){
			pActiveAcad->QueryInterface( &pAcad );
			QuitOnClose = false;
		}else{
			Res = pAcad.CoCreateInstance(CLSID_AcadApplication,0,CLSCTX_LOCAL_SERVER);
			QuitOnClose = true;
		}
		CComPtr<IAcadState> pState;
		do{
			Res = pAcad->GetAcadState(&pState);
		}while(Res!=S_OK);
		do{
		}while(!pState->IsQuiescent);
	}

	if( pDSPUtils==NULL ){
		TCHAR Filename[1024];
		Res = pAcad->put_Visible(VARIANT_TRUE);
		GetModuleFileName( hThisInstance, Filename,1023 );
		TCHAR Drive[8],FilePath[1024];
		_wsplitpath(Filename,Drive,FilePath,NULL,NULL);
		_bstr_t ArxName(Drive);
		ArxName+=_bstr_t(FilePath);
		ArxName+=_bstr_t("IMDSPSrv.arx");

		
		Res = pAcad->LoadArx(ArxName);
		CComPtr<IDispatch> pDisp;
		Res = pAcad->GetInterfaceObject(_bstr_t("DSPSrv.DSPUtils"),&pDisp);
		if( pDisp )
			Res = pDisp->QueryInterface(&pDSPUtils);
	}
}

STDMETHODIMP CApp::CreateDWG(BSTR FullPath, DOUBLE MinX, DOUBLE MinY, DOUBLE MaxX, DOUBLE MaxY)
{
	InitAcad();
	CComPtr<IAcadDocument> pDoc;
	HRESULT Res = pAcad->Documents->Add( vtMissing, &pDoc );
	
	CComSafeArray<double> pVerticies;
	pVerticies.Create();
	pVerticies.Add( MinX );	pVerticies.Add( MinY );
	pVerticies.Add( MinX );	pVerticies.Add( MaxY );
	pVerticies.Add( MaxX );	pVerticies.Add( MaxY );
	pVerticies.Add( MaxX );	pVerticies.Add( MinY );

	CComPtr<IAcadLWPolyline> pPolyline;
	Res = pDoc->Database->ModelSpace->AddLightWeightPolyline( CComVariant(pVerticies), &pPolyline );
	Res = pPolyline->put_Closed( VARIANT_TRUE );

	Res = pAcad->ZoomExtents();

	Res = pDoc->SaveAs( FullPath );
	if( FAILED(Res) ){
		_bstr_t MsgStr("Невозможно сохранить файл\"");
		MsgStr+=_bstr_t(FullPath);
		MsgStr+=_bstr_t("\"");
		::MessageBox(0,MsgStr,NULL,MB_OK+MB_SYSTEMMODAL+MB_ICONERROR);
	}
	return Res;
}

STDMETHODIMP CApp::GetData(BSTR DWGFullPath, DOUBLE* LineLength, DOUBLE* CurveLeng, DOUBLE* Square, LONG* RegionCount)
{
	InitAcad();

	if( pDSPUtils )
		pDSPUtils->GetData(DWGFullPath, LineLength, CurveLeng, Square, RegionCount );
/*
	*LineLength=0.0;
	*CurveLeng=0.0;
	*Square=0.0;

	CComPtr<IAcadDocument> pDoc;
	char FileName[256];
	_splitpath(_bstr_t(DWGFullPath),NULL,NULL,FileName,NULL);
	_bstr_t bstrFileName(FileName);
	bstrFileName+=_bstr_t(".dwg");
	HRESULT Res = pAcad->Documents->Item(CComVariant((BSTR)bstrFileName), &pDoc );
	if( pDoc==NULL )
		Res = pAcad->Documents->Open( DWGFullPath, vtMissing, vtMissing, &pDoc );
	if( pDoc==NULL ){
		_bstr_t bstrMessage("Документ \"");
		bstrMessage+=_bstr_t(DWGFullPath);
		bstrMessage+=_bstr_t("\" не найден");
		::MessageBox(0,bstrMessage,0,MB_OK+MB_SYSTEMMODAL+MB_ICONERROR);
	}
	CComPtr<IAcadModelSpace> pModelSpace = pDoc->Database->ModelSpace;
	long EntCount = pModelSpace->Count;
	for( long i=0; i<EntCount; i++ ){
		CComPtr<IAcadEntity> pEnt;
		Res = pModelSpace->Item( CComVariant(i), &pEnt );
		_bstr_t EntName = pEnt->EntityName;
		long EntType = pEnt->EntityType;
		switch( EntType ){
			case 19:
				{
					CComQIPtr<IAcadLine> pLine = pEnt;
					*LineLength += pLine->Length;
				}break;
			case 4:
				{
					CComQIPtr<IAcadArc> pArc = pEnt;
					*CurveLeng += pArc->ArcLength;
				}break;
			case 8:
				{
					CComQIPtr<IAcadCircle> pCircle = pEnt;
					*CurveLeng += pCircle->Circumference;
				}break;
			case 16:
				{
					CComQIPtr<IAcadEllipse> pEllipce = pEnt;
				}break;
		}
	}
*/
	return S_OK;
}
