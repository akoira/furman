// App.h : Declaration of the CApp

#pragma once
#include "resource.h"       // main symbols

#include "DSP_i.h"


#if defined(_WIN32_WCE) && !defined(_CE_DCOM) && !defined(_CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA)
#error "Single-threaded COM objects are not properly supported on Windows CE platform, such as the Windows Mobile platforms that do not include full DCOM support. Define _CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA to force ATL to support creating single-thread COM object's and allow use of it's single-threaded COM object implementations. The threading model in your rgs file was set to 'Free' as that is the only threading model supported in non DCOM Windows CE platforms."
#endif



// CApp

class ATL_NO_VTABLE CApp :
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CApp, &CLSID_App>,
	public IDispatchImpl<IApp, &IID_IApp, &LIBID_DSPLib, /*wMajor =*/ 1, /*wMinor =*/ 0>
{
public:
	CApp()
	{
		QuitOnClose = true;
	}

DECLARE_REGISTRY_RESOURCEID(IDR_APP)


BEGIN_COM_MAP(CApp)
	COM_INTERFACE_ENTRY(IApp)
	COM_INTERFACE_ENTRY(IDispatch)
END_COM_MAP()



	DECLARE_PROTECT_FINAL_CONSTRUCT()

	HRESULT FinalConstruct()
	{
		return S_OK;
	}

	void FinalRelease()
	{
		pDSPUtils.Release();
		if( QuitOnClose )
			pAcad->Quit();
	}

public:
	CComPtr<IAcadApplication> pAcad;
	CComPtr<IDSPUtils> pDSPUtils;
	bool QuitOnClose;
	void InitAcad(void);

	STDMETHOD(CreateDWG)(BSTR FullPath, DOUBLE MinX, DOUBLE MinY, DOUBLE MaxX, DOUBLE MaxY);
	STDMETHOD(GetData)(BSTR DWGFullPath, DOUBLE* LineLength, DOUBLE* CurveLeng, DOUBLE* Square, LONG* RegionCount);
};

OBJECT_ENTRY_AUTO(__uuidof(App), CApp)
